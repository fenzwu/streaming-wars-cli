# `update_demo` Test Cases
## Success Cases
1. Update the `longName` of an existing demographic group at the beginning of a time period.
1. Update the `longName` of an existing demographic group not at the beginning of a time period.
1. Update the `numOfAccounts` of an existing demographic group at the beginning of a time period.
1. Update the `longName` and `numOfAccounts` of an existing demographic group.

## Invalid or Failure Cases
1. Update the `longName` of a nonexistent demographic group.
1. Update the `numOfAccounts` of a nonexistent demographic group.
1. Update the `longName` and `numOfAccounts` of a nonexistent demographic group.
1. Update the `numOfAccounts` of an existing demographic group not at the beginning of a time period.

__Time Period Requirement__
> The number of accounts can be changed only at the beginning of a time period before that
specific demographic group has accessed and viewed any movies or PPV events.

## Test Commands
// creates demographic group \
`create_demo,age_20_heroes,Viewers of Marvel/DC under 20,10000` 

// displays a longName of __Viewers of Marvel/DC under 20__ and __10000__ accounts in the demographic group \
`display_demo,age_20_heroes`

// _successfully_ updates the longName of the demographic group to __Young Comic Book Readers__ and number of accounts to __100__. \
`update_demo,age_20_heros,Young Comic Book Readers,100`

// displays the successfully updated longName of __Young Comic Book Readers__ and updated number of accounts of __100__. \
`display_demo,age_20_heroes`

// demographic group watches a movie \
`watch_event,age_20_heroes,60,hulu,Batman v Superman,2016`

// _unsuccessfully_ attempts to update the number of accounts to __200__. \
`update_demo,age_20_heros,Young Comic Book Readers,200`

// displays the unchanged longName of __Young Comic Book Readers__ and unchanged number of accounts of __100__. \
`display_demo,age_20_heroes`

// change to next month \
`next_month`

// displays the unchanged longName of __Young Comic Book Readers__ and unchanged number of accounts of __100__. \
`display_demo,age_20_heroes`

// _successfully_ updates the longName to __Teen Fans of Marvel/DC__ and number of accounts to __300__. \
`update_demo,age_20_heros,Teen Fans of Marvel/DC,300`

// displays the updated longName of __Teen Fans of Marvel/DC__ and updated number of accounts of __300__. \
`display_demo,age_20_heroes`

# `update_stream` Test Cases
## Success Cases
1. Update the `longName` of an existing streaming service at the beginning of a time period.
1. Update the `longName` of an existing streaming service not at the beginning of a time period.
1. Update the `subscriptionPrice` of an existing streaming service at the beginning of a time period.
1. Update the `longName` and `subscriptionPrice` of an existing streaming service.

## Invalid or Failure Cases
1. Update the `longName` of a nonexistent streaming service.
1. Update the `subscriptionPrice` of a nonexistent streaming service.
1. Update the `longName` and `subscriptionPrice` of a nonexistent streaming service.
1. Update the `subscriptionPrice` of an existing streaming service not at the beginning of a time period.

__Time Period Requirement__
> The subscription price can be changed only at the beginning of a time period before that specific streaming service
> has not been used to access and view any moves.


## Test Commands
// creates the streaming service \
`create_stream,hulu,Hulu Plus,11` 

// displays a longName of __Hulu Plus__ and subscription price of __11__. \
`display_stream,hulu`

// _successfully_ updates the longName of the streaming service to __Just Hulu__ and subscription price to __3__. \
`update_stream,hulu,Just Hulu,3`

// displays the successfully updated longName of __Just Hulu__ and updated subscription price of __3__. \
`display_stream,hulu`

// demographic group watches a movie on streaming service __hulu__. \
`watch_event,age_20_heroes,60,hulu,Batman v Superman,2016`

// _unsuccessfully_ attempts to update the subscription price to __5__. \
`update_stream,hulu,Just Hulu,5`

// displays the unchanged longName of __Just Hulu__ and subscription price of __3__. \
`display_stream,hulu`

// change to next month \
`next_month`

// displays the unchanged longName of __Just Hulu__ and unchanged subscription price of __3__. \
`display_stream,hulu`

// _successfully_ updates the longName to __Netflix with Ads__ and subscription price to __20__. \
`update_stream,hulu,Netflix with Ads,20`

// displays the updated longName of __Netflix with Ads__ and updated subscription price of __20__. \
`display_stream,hulu`
