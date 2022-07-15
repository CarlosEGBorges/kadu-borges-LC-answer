drop table movie_card if exists;
create table movie_card (
    imdb_id varchar(12) not null primary key,
    title varchar(100) not null,
    released_year varchar(9) not null,
    imdb_rating double precision not null,
    imdb_votes integer not null,
    poster_url varchar(256)
);

drop table answer if exists;
create table answer (
    id identity,
    movie_card_id varchar(12) not null,
    correct boolean not null,
    created_at timestamp
);
alter table answer add constraint fk_answer_movie_card foreign key (movie_card_id) references movie_card(imdb_id) on delete restrict;

drop table question if exists;
create table question (
    id identity,
    quiz_id varchar(36) not null,
    card_one varchar(12) not null,
    card_two varchar(12) not null,
    answer_id integer
);
alter table question add constraint fk_question_movie_card_one foreign key (card_one) references movie_card(imdb_id) on delete restrict;
alter table question add constraint fk_question_movie_card_two foreign key (card_two) references movie_card(imdb_id) on delete restrict;
alter table question add constraint fk_question_answer foreign key (answer_id) references answer(id) on delete restrict;

drop table quiz if exists;
create table quiz (
    id identity,
    username varchar(256) not null,
    mistakes integer not null,
    active boolean not null,
    created_at timestamp not null,
    version integer
);
alter table question add constraint fk_question_quiz foreign key (quiz_id) references quiz(id) on delete restrict;
