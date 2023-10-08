DROP TABLE IF EXISTS users_scheme.users CASCADE;
DROP TABLE IF EXISTS users_scheme.subscriptions CASCADE;
DROP TABLE IF EXISTS users_scheme.cities CASCADE;
DROP SCHEMA IF EXISTS users_scheme CASCADE;
CREATE SCHEMA users_scheme;

CREATE TABLE users_scheme.cities
(
    id  uuid NOT NULL DEFAULT gen_random_uuid(),
    title text NOT NULL,
    PRIMARY KEY (id)
);

CREATE INDEX i_city_title_b_tree
    ON users_scheme.cities USING btree
        (title ASC NULLS LAST);


CREATE TABLE users_scheme.users
(
    id uuid NOT NULL DEFAULT gen_random_uuid(),
    first_name text NOT NULL,
    last_name text NOT NULL,
    middle_name text,
    email text NOT NULL,
    password text NOT NULL,
    phone text NOT NULL,
    city text DEFAULT '',
    registration_date TIMESTAMP WITH TIME ZONE NOT NULL,
    update_date TIMESTAMP WITH TIME ZONE NOT NULL,
    is_deleted boolean NOT NULL DEFAULT False,
    PRIMARY KEY (id),
    CONSTRAINT constr_unique_email UNIQUE (email),
    CONSTRAINT constr_unique_phone UNIQUE (phone)
);

ALTER TABLE IF EXISTS users_scheme.users
    OWNER to micro;

CREATE TABLE users_scheme.subscriptions
(
    user_id uuid NOT NULL,
    subscription_id uuid NOT NULL,
    subscription_date TIMESTAMP WITH TIME ZONE NOT NULL,
    PRIMARY KEY (user_id, subscription_id),
    CONSTRAINT constr_user_id_on_id FOREIGN KEY (user_id)
        REFERENCES users_scheme.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT constr_subscription_id_on_id FOREIGN KEY (subscription_id)
        REFERENCES users_scheme.users (id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID,
    CONSTRAINT constr_user_id_not_subscription_id
        CHECK (user_id != subscription_id)
            NOT VALID
);

CREATE INDEX i_user_id_b_tree
    ON users_scheme.subscriptions USING btree
        (user_id ASC NULLS LAST);


ALTER TABLE IF EXISTS users_scheme.subscriptions
    OWNER to micro;


