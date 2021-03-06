DROP TABLE IF EXISTS "user";

CREATE TABLE "user" (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  email VARCHAR NOT NULL
);

CREATE TABLE secret (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  hash VARCHAR NOT NULL,
  author_id BIGINT NOT NULL REFERENCES "user"(id) ON DELETE CASCADE,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL
);