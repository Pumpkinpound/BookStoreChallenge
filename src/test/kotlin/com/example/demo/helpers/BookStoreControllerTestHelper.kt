//package com.example.demo.helpers
//
//import org.springframework.http.HttpEntity
//import org.springframework.http.HttpHeaders
//import java.net.URI
//import java.util.*
//
//class BookStoreControllerTestHelper {
//    companion object{
//        const val DEFAULT_USERNAME = "john.joe"
//        const val DEFAULT_PASSWORD = "thisismysecret"
//        const val DEFAULT_DATEOFBIRTH = Date("2018-12-12")
//        /**
//         * Build request URI
//         */
//        fun buildRequestUri(port: Int, word: String): URI {
//            return URI("http://localhost:$port/$word")
//        }
//        /**
//         * Build request entity
//         */
//        fun buildIdentityBulkRequestEntity(
//            username: String = DEFAULT_USERNAME,
//            password: String = DEFAULT_PASSWORD,
//            dateOfBirth: Date = DEFAULT_DATEOFBIRTH
//        ): HttpEntity<CbMqSendSuccessModel> {
//            val headers = HttpHeaders()
//            headers.set("Content-Type", "application/json")
//
//            val requestModel = CbMqSendSuccessModel(
//                requestId,
//                nodeId,
//                type,
//                destinationNodeId,
//                destinationIp,
//                destinationPort
//            )
//            return HttpEntity(requestModel, headers)
//        }
//    }
//
//}