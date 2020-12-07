CREATE SCHEMA IF NOT EXISTS java3;

CREATE TABLE IF NOT EXISTS wishList(
                      id    bigserial PRIMARY KEY,
                      wish varchar(200),
                      status varchar(200) --TODO postgre enum?
);
