INSERT INTO users (username, password, enabled)
VALUES ('kaduBorges', '$2a$04$te2IUS.AI86REaPyUzy4reBzH1Qzss0Ol1IeHTWRwShkt7UUYY2pu', true);
INSERT INTO users (username, password, enabled)
VALUES ('usuario1', '$2a$04$te2IUS.AI86REaPyUzy4reBzH1Qzss0Ol1IeHTWRwShkt7UUYY2pu', true);
INSERT INTO users (username, password, enabled)
VALUES ('usuario2', '$2a$04$te2IUS.AI86REaPyUzy4reBzH1Qzss0Ol1IeHTWRwShkt7UUYY2pu', true);

INSERT INTO groups (id, group_name)
VALUES (1, 'CINEPHILE_GROUP');

INSERT INTO group_authorities (group_id, authority)
VALUES (1, 'REGULAR_MATCH');

INSERT INTO group_members (username, group_id)
VALUES ('kaduBorges', 1);
INSERT INTO group_members (username, group_id)
VALUES ('usuario1', 1);
INSERT INTO group_members (username, group_id)
VALUES ('usuario2', 1);

INSERT INTO oauth_client_details (client_id, client_secret, resource_ids, scope, authorized_grant_types, access_token_validity, refresh_token_validity)
VALUES ('appclient', '$2a$04$te2IUS.AI86REaPyUzy4reBzH1Qzss0Ol1IeHTWRwShkt7UUYY2pu', 'movieBattle', 'read,write', 'authorization_code,check_token,refresh_token,password', 600, 600);