from fastapi import FastAPI, HTTPException, Form, Request
from sqlmodel import Session, select
from datetime import datetime, timezone
from models import PaymentRequest, PaymentMethod, PaymentStatus
from database import init_db, engine
import uuid
import hmac
import hashlib
import requests
import os
from dotenv import load_dotenv
from typing import Optional
from urllib.parse import urlencode

load_dotenv()



#####################################################
# Constants Momo
ACCESS_KEY = os.getenv("ACCESS_KEY")
SECRET_KEY = os.getenv("SECRET_KEY")
PARTNER_CODE = os.getenv("PARTNER_CODE")
REDIRECT_URL = os.getenv("REDIRECT_URL")
IPN_URL = os.getenv("IPN_URL")
ENDPOINT = "https://test-payment.momo.vn/v2/gateway/api/create"
#####################################################




#####################################################
# VNpay
VNPAY_TMN_CODE = os.getenv("VNPAY_TMN_CODE")
VNPAY_HASH_SECRET_KEY = os.getenv("VNPAY_HASH_SECRET_KEY")
VNPAY_PAYMENT_URL = os.getenv("VNPAY_PAYMENT_URL")
VNPAY_RETURN_URL = os.getenv("VNPAY_RETURN_URL")
#####################################################





# Initialize FastAPI app
app = FastAPI()
init_db()

def generate_signature(data: str, secret_key: str) -> str:
    """Generate HMAC SHA256 signature."""
    return hmac.new(bytes(secret_key, "ascii"), bytes(data, "ascii"), hashlib.sha256).hexdigest()

def send_request_to_momo(data: dict) -> dict:
    """Send request to MoMo API."""
    try:
        response = requests.post(ENDPOINT, json=data, headers={"Content-Type": "application/json"})
        response.raise_for_status()  # Raise an exception for HTTP errors
        return response.json()
    except requests.RequestException as e:
        raise HTTPException(status_code=500, detail=f"Error connecting to MoMo: {str(e)}")

@app.post("/create_payment")
def create_payment(payment: PaymentRequest):
    try:
        order_id = str(uuid.uuid4())
        request_id = str(uuid.uuid4())
        extra_data = ""
        request_type = "payWithMethod"

        # Generate raw signature
        raw_signature = (
            f"accessKey={ACCESS_KEY}&amount={payment.amount}&extraData={extra_data}&ipnUrl={IPN_URL}&"
            f"orderId={order_id}&orderInfo={payment.orderInfo}&partnerCode={PARTNER_CODE}&"
            f"redirectUrl={REDIRECT_URL}&requestId={request_id}&requestType={request_type}"
        )
        signature = generate_signature(raw_signature, SECRET_KEY)

        # Prepare data payload
        data = {
            "partnerCode": PARTNER_CODE,
            "orderId": order_id,
            "amount": payment.amount,
            "orderInfo": payment.orderInfo,
            "redirectUrl": REDIRECT_URL,
            "ipnUrl": IPN_URL,
            "requestId": request_id,
            "extraData": extra_data,
            "lang": "vi",
            "requestType": request_type,
            "signature": signature,
        }

        # Send request to MoMo
        response_data = send_request_to_momo(data)

        # Save payment to database
        with Session(engine) as session:
            payment.id = order_id
            payment.status = PaymentStatus.pending
            session.add(payment)
            session.commit()

        return response_data

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.get("/get_payment/{payment_id}")
def get_payment(payment_id: str):
    try:
        with Session(engine) as session:
            payment = session.exec(select(PaymentRequest).where(PaymentRequest.id == payment_id)).first()

        if payment:
            return payment
        raise HTTPException(status_code=404, detail="Payment not found")

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))

@app.put("/update_payment/{payment_id}")
def update_payment(payment_id: str, status: PaymentStatus):
    try:
        with Session(engine) as session:
            payment = session.exec(select(PaymentRequest).where(PaymentRequest.id == payment_id)).first()

            if not payment:
                raise HTTPException(status_code=404, detail="Payment not found")

            payment.status = status
            session.commit()
            return {"message": f"Payment status updated to {status}"}

    except Exception as e:
        raise HTTPException(status_code=500, detail=str(e))





