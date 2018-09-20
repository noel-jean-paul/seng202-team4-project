drop table profile;
drop table goal;
drop table activity;


create table profile (
firstName text not null,
lastName text not null,
dateOfBirth character(10) not null,
height real constraint check_height check (height BETWEEN 1.00 and 3.00),
weight real constraint check_weight check (weight between 0 and 250),
primary key (firstName, lastName)
);


create table goal (
goalNumber integer,
progress integer constraint check_progress check (progress between 0 and 100),
type character(3) constraint check_type check (type in ("Run", "Walk", "Other")),
description text,
creationDate character(10) not null,
expiryDate character(10),
completionDate character(10),
goalDuration character(8),
goalDistance real,
firstName text,
lastName text,
primary key (firstName, lastName, goalNumber),
foreign key (firstName, lastName) references profile
on delete cascade on update no action
);


create table activity (
name text,
activityDate character(10),
description text,
type character(3) constraint check_type check (type in ("Run", "Walk", "Other")),
startTime character(8) not null,
duration character(8) not null,
distance real not null,
caloriesBurned integer not null,
firstName text,
lastName text,
primary key (name, activityDate, firstName, lastName),
foreign key (firstName, lastName) references profile
on delete cascade
);

drop table dataRow;

create table dataRow (
  rowNumber integer,
  rowDate character(10),
  time character(8) not null,
  heartRate integer constraint check_heartRate check (heartRate between 60 and 250),
  latitude double constraint check_latitude check (latitude between -90 and 90),
  longitude double constraint check_longitude check (longitude between -180 and 180),
  elevation double constraint check_elevation check (elevation between 0 and 4000),
  name text,
  activityDate character(10),
  firstName text not NULL,
  lastName text not null,
  foreign key (firstName, lastName) references Profile,
  primary key (firstName, lastName, name, activityDate, rowNumber)
);