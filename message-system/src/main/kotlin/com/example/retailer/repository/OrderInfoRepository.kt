package com.example.retailer.repository

import com.example.retailer.api.distributor.OrderInfo
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderInfoRepository : CrudRepository<OrderInfo, String>