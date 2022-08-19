package com.example.demo.component

import com.example.demo.model.EachBook
import com.example.demo.service.UserDetailService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class ScheduledTasks(
    private val userDetailService: UserDetailService
) {
    companion object{
        const val uri = "https://scb-test-book-publisher.herokuapp.com/books"
        const val uriRecommended = "https://scb-test-book-publisher.herokuapp.com/books/recommendation"
    }

    @Scheduled(fixedRate = 1000*60*60*24)
    fun fetchDataToLocalDB() {
        val restTemplate = RestTemplate()
        val res = restTemplate.getForObject(uri, Array<EachBook>::class.java)!!.toList()
        val resRecommended = restTemplate.getForObject(uriRecommended, Array<EachBook>::class.java)!!.toList()
        for (x in res) {
            for (y in resRecommended) {
                if (x.id == y.id) {
                    userDetailService.updateLocalBookStoreInfo(x,true)
                    break
                } else {
                    userDetailService.updateLocalBookStoreInfo(x,false)
                }
            }
        }
    }
}

