org.springframework.cloud.contract.spec.Contract.make {
  
  request {
    method 'POST'
    url '/project/add'
    body([
    
   "name":$(consumer(regex('[a-zA-Z0-9]+')), producer('ABC')),
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
      fromRequest().body('$.name')
    )
 }
}
