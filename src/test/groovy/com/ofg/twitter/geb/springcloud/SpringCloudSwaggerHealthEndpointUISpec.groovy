package com.ofg.twitter.geb.springcloud

import com.ofg.twitter.geb.SwaggerHealthEndpointUISpec
import org.springframework.boot.test.IntegrationTest

@IntegrationTest("spring.profiles.active:dev,springCloud")
class SpringCloudSwaggerHealthEndpointUISpec extends SwaggerHealthEndpointUISpec {

}
