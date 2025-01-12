from datetime import datetime, timezone
from sqlmodel import SQLModel, Field
from typing import Optional
from enum import Enum


# Enum cho phương thức thanh toán
class PaymentMethod(str, Enum):
    VNPay = "VNPay"
    ZaloPay = "ZaloPay"
    MoMo = "MoMo"
    CreditCard = "CreditCard"
    DebitCard = "DebitCard"

# Enum cho trạng thái thanh toán
class PaymentStatus(str, Enum):
    pending = "pending"
    completed = "completed"
    failed = "failed"
    cancelled = "cancelled"


class PaymentRequest(SQLModel, table=True):
    id: int = Field(default=None, primary_key=True)
    user_id: int = Field(..., nullable=False) 
    booking_id: int = Field(..., nullable=False) 
    amount: int = Field(..., nullable=False)  
    orderInfo: str = Field(..., nullable=False)  
    payment_date: datetime = Field(default_factory=lambda: datetime.now(timezone.utc)) 
    payment_method: PaymentMethod = Field(..., nullable=False)  
    currency: str = Field(default="VND", nullable=False)  
    transaction_id: Optional[str] = Field(default=None, nullable=True) 
    status: PaymentStatus = Field(default=PaymentStatus.pending, nullable=False)  
    payment_details: Optional[str] = Field(default=None, nullable=True)  
    payment_response: Optional[str] = Field(default=None, nullable=True) 
