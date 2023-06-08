create table if not exists emails (
    id int not null auto_increment,
    emailFrom varchar(50) not null,
    emailTo varchar(50) not null,
    subject varchar(50) not null,
    text varchar(50) not null,
    primary key(id)
    );
