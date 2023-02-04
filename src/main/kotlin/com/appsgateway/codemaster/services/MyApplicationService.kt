package com.appsgateway.codemaster.services

import com.appsgateway.codemaster.MyBundle

class MyApplicationService {

    init {
        println(MyBundle.message("applicationService"))
        println("TEST MyApplicationService");
//        System.getenv("CI")
//            ?: TODO("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }
}
