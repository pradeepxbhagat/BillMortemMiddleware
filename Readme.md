Api list

Server: https://enigmatic-falls-78460.herokuapp.com

1. get Transaction list from pdf
url : /service/report/get_transactions
input: mutlipart upload
method: post
on success - 
    response- list of transactions
    type- json
on Error - 
    1. response - Invalid pdf file, error code - 1302
       type - json
    2. response - Password required, error code - 1303
           type - json
    2. response - Password invalid, error code - 1304
               type - json
               
2. get DistinctKeyTotalReport
url : /service/report/get_distinct_report
input: transaction report in jsonw
method: post
    on Success -
        response - map of title and total
        type - json
    on Error -
        response- Not able to process, error code - 1301
 
3. get ArtificialDistinctKeyTotalReport
url : /service/report/get_intelligent_report
input: transaction report in json
method: post
    on Success -
        response - map of title and total
        type - json
    on Error -
        response- Not able to process, error code - 1301
        
4. Search
url : /service/search
input: search term in comma seperated in json
method: post
    on Success -
        response - map of title and transaction reports
        type - json
    on Error -
        response- Not able to process, error code - 1301