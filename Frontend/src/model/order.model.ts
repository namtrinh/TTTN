
export class Order {
  orderId!: string
  name!: string;
  phone!: number
  email!: string
  orderDate!: string
  orderStatus!: OrderStatus
  totalPrice!: number;
  paymentMethod!: string

}

export enum OrderStatus{
  SUCCESS = "SUCCESS",
CANCELLED = "CANCELLED"
}
