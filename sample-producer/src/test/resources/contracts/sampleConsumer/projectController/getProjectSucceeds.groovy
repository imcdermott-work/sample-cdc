org.springframework.cloud.contract.spec.Contract.make {
	request {
        method('GET')
		url $(consumer(regex('/project/[a-zA-Z0-9]+')), producer('/project/12'))
	}

	response {
		status 200
        body(
            name: $(consumer('consumerExampleTest'), producer(regex('[a-zA-Z0-9]+'))),
            users: [
                [
                id: $(consumer('777'), producer(regex('.+'))),
                name: $(consumer('sampleUser'), producer(regex('.+')))
                ]
            ]
        )
        testMatchers {
			jsonPath('$.users', byCommand('assertThatListContainsValidUsers($it)'))
        }
        headers{
            header('Content-Type', 'application/json;charset=UTF-8')
            header('Access-Control-Allow-Origin', '*')
        }
	}
}