org.springframework.cloud.contract.spec.Contract.make {
  
  request {
    method 'POST'
    url '/project/add'
    body([
    //Here we are saying that the consumer should be able to pass in any hex value, but the test on the producer side should
    //use B4311BB0A9C833BA3C0C3E723BF9C97B as
   "name":$(consumer(regex('[a-zA-Z0-9]+')), producer('B4311BB0A9C833BA3C0C3E723BF9C97B')),
   "users":[  
             [
               id: $(consumer(regex('[a-zA-Z0-9]+')), producer('111')),
               name: $(consumer(regex('[a-zA-Z0-9]+')), producer('Admin'))
             ]
           ]
    ])
    headers {
      header('Content-Type', 'application/json;charset=UTF-8')
    }
  }
response {
  status 200
  body(
      //the body should contain the resourceId from the request in the case of the addSegment method.
      fromRequest().body('$.name')
      //'B4311BB0A9C833BA3C0C3E723BF9C97B'
    )
 }
}