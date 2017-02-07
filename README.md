# money
Money as a datatype. Born out of the observation that money in different currencies 
and other fungible goods (oil, gold, shares) can be represented as an abelian group
(vector space) and as such it has been coded in cats. 

Also includes protection to ensure that monetary values are only represented using 'safe'
data types (integers, strings, big decimals) and not floating points.
