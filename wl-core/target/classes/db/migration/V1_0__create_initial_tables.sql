CREATE SCHEMA IF NOT EXISTS abc;

CREATE TABLE IF NOT EXISTS wishList(
                      id    bigserial PRIMARY KEY,
                      wish varchar(200),
                      status varchar(200)
);
