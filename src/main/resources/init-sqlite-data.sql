drop table if exists t_topping_counts;
drop table if exists t_users;

create table t_topping_counts(
  topping_name    text       primary key not null,
  total_count     integer    not null,
  unique_count    integer    not null
);

create table t_users(
  user_id         integer primary key autoincrement,
  topping_name    text       not null,
  foreign key (topping_name) references t_topping_counts(topping_name)
);

insert into t_topping_counts(topping_name, total_count, unique_count) values ('cheese', 120, 100);
insert into t_topping_counts(topping_name, total_count, unique_count) values ('pepperoni', 40, 35);
insert into t_topping_counts(topping_name, total_count, unique_count) values ('sausage', 33, 30);
insert into t_topping_counts(topping_name, total_count, unique_count) values ('mushroom', 25, 25);
insert into t_topping_counts(topping_name, total_count, unique_count) values ('olive', 11, 10);
insert into t_topping_counts(topping_name, total_count, unique_count) values ('banana pepper', 9, 9);
insert into t_topping_counts(topping_name, total_count, unique_count) values ('green pepper', 12, 10);
insert into t_topping_counts(topping_name, total_count, unique_count) values ('chili flakes', 9, 5);