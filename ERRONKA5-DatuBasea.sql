drop database if exists erronka5;
create database erronka5;
use erronka5;

create table biltegia(
    biltegi_kod int not null,
    izena varchar(150) not null,
    kokapena varchar(150) not null,
    primary key(biltegi_kod)
);

create table rola(
    id_rola int not null,
    izena varchar(150),
    primary key(id_rola)
);

create table donatzaileak(
    donatzaile_nan varchar(9),
    izena varchar(150),
    abizena varchar(200),
    primary key(donatzaile_nan)
);

create table produktuak(
    id int not null unique auto_increment,
    biltegi_kod int not null,
    izena varchar(100),
    kokapen_kod int not null,
    pasilo_zenb int not null,
    erreferentzia varchar(30) not null,
    mota enum('Iragankorra', 'Erdi-Iragankorra', 'Ez-Iragankorra') not null,
    fabrikatzailea varchar(150),
    primary key(id),
    foreign key(biltegi_kod) references biltegia(biltegi_kod)
);

create table erabiltzaileak(
    id_erabiltzaile int not null auto_increment,
    izena varchar(150) not null,
    email varchar(200) not null,
    pasahitza varchar(200) not null,
    id_rola int not null,
    primary key(id_erabiltzaile),
    foreign key(id_rola) references rola(id_rola)
);

create table iragankorrak(
    prod_id int not null,
    hoztea boolean null,
    primary key(prod_id),
    foreign key(prod_id) references produktuak(id)
);

create table erdi_iragankorrak(
    prod_id int not null,
    hozeta boolean null,
    hezetasun_max double check(hezetasun_max >= 0 and hezetasun_max <= 100) not null,
    primary key(prod_id),
    foreign key(prod_id) references produktuak(id)
);

create table ez_iragankorrak(
    prod_id int not null,
    kontserba boolean null,
    primary key(prod_id),
    foreign key(prod_id) references produktuak(id)
);

create table donazioak(
    id_donazio int not null auto_increment,
    prod_id int not null,
    donatzaile_nan varchar(9),
    data date not null,
    kantitatea int not null,
    primary key(id_donazio),
    foreign key(prod_id) references produktuak(id),
    foreign key(donatzaile_nan) references donatzaileak(donatzaile_nan)
);

create table irteerak(
    id_irteera int not null auto_increment,
    prod_id int not null,
    helmuga varchar(200) not null,
    bidalketa_data date not null,
    kantitatea int not null,
    primary key(id_irteera),
    foreign key(prod_id) references produktuak(id)
);

create table stock(
    biltegi_kod int not null,
    prod_id int not null,
    iraungitze_data date not null,
    kopurua int not null,
    primary key(biltegi_kod, prod_id, iraungitze_data),
    foreign key(biltegi_kod) references biltegia(biltegi_kod),
    foreign key(prod_id) references produktuak(id)
);
