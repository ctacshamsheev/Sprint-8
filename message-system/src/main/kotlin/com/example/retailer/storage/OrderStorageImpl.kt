package com.example.retailer.storage

import com.example.retailer.api.distributor.Order
import com.example.retailer.api.distributor.OrderInfo
import com.example.retailer.api.distributor.OrderStatus
import com.example.retailer.repository.OrderInfoRepository
import com.example.retailer.repository.OrderRepository
import org.springframework.stereotype.Component

@Component
class OrderStorageImpl(
    private val orderRepository: OrderRepository,
    private val orderInfoRepository: OrderInfoRepository,
) : OrderStorage {

    override fun createOrder(draftOrder: Order): PlaceOrderData {
        val order = orderRepository.save(draftOrder)
        if (order.id == null) {
            throw IllegalArgumentException("Order.id is null")
        }
        val draftOrderInfo = OrderInfo(order.id, OrderStatus.SENT, "")
        val orderInfo = orderInfoRepository.save(draftOrderInfo)
        return PlaceOrderData(order, orderInfo)
    }

    override fun updateOrder(order: OrderInfo): Boolean {
        if (orderInfoRepository.existsById(order.orderId)) {
            orderInfoRepository.save(order)
            return true
        }
        return false
    }

    override fun getOrderInfo(id: String): OrderInfo? {
        return orderInfoRepository.findById(id).orElse(null)
    }
}