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