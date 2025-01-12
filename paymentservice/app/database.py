from sqlmodel import SQLModel, create_engine, Session
from models import PaymentRequest

db_username = "root"
db_password = ""
db_host = "localhost"  
db_port = "3306"
db_name = "payment"


database_url = f"mysql+pymysql://{db_username}:{db_password}@{db_host}:{db_port}/{db_name}"
engine = create_engine(database_url, echo=True)
def init_db():
    SQLModel.metadata.create_all(engine)

def get_session():
    with Session(engine) as session:
        yield session
        
if __name__ == "__main__":
    init_db()
    print("Database initialized successfully!")
