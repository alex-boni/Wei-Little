#create database bdvideojuegos;
use bdvideojuegos;
/*
drop table LINEAVENTA;
drop table PRODUCTOENPLATAFORMA;
drop table PLATAFORMA;
drop table VIDEOJUEGO;
drop table COMPLEMENTO;
drop table PRODUCTO;
drop table SUPERVISOR;
drop table VENDEDOR;
drop table TRABAJADORHABILIDAD;
drop table HABILIDAD;
drop table VENTAS;
drop table TRABAJADOR;
*/
create table PLATAFORMA(
id_plataforma int unsigned auto_increment primary key,
nombre varchar(100) not null,
activo int not null 
);

create table PRODUCTO(
id_producto int unsigned auto_increment primary key,
nombre varchar(100) not null,
marca varchar(100) not null,
activo int not null
);
create table VIDEOJUEGO(
id_producto int unsigned primary key,
restriccion_edad int unsigned not null,
activo int not null,
FOREIGN KEY (id_producto) references PRODUCTO(id_producto) ON UPDATE CASCADE ON DELETE CASCADE
);
create table COMPLEMENTO(
id_producto int unsigned primary key,
peso double not null,
activo int not null,
FOREIGN KEY (id_producto) references PRODUCTO(id_producto) ON UPDATE CASCADE ON DELETE CASCADE
);

create table PRODUCTOENPLATAFORMA(
id_producto_final int unsigned auto_increment primary key,
id_producto int unsigned not null,
id_plataforma int unsigned not null,
cantidad int not null,
precio_venta_actual double not null,
activo int not null,
FOREIGN KEY (id_producto) references PRODUCTO(id_producto) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (id_plataforma) references PLATAFORMA(id_plataforma) ON UPDATE CASCADE ON DELETE CASCADE
);

create table TRABAJADOR(
id_trabajador int unsigned auto_increment primary key,
dni varchar(9) unique not null,
nombre varchar(100) not null,
activo int not null
);
create table VENDEDOR(
id_trabajador int unsigned primary key,
idioma varchar(100) not null,
activo int not null,
FOREIGN KEY (id_trabajador) references TRABAJADOR(id_trabajador) ON UPDATE CASCADE ON DELETE CASCADE
);
create table SUPERVISOR(
id_trabajador int unsigned primary key,
experiencia varchar(100) not null,
activo int not null,
FOREIGN KEY (id_trabajador) references TRABAJADOR(id_trabajador) ON UPDATE CASCADE ON DELETE CASCADE
);

create table HABILIDAD(
id_habilidad int unsigned auto_increment primary key,
nombre varchar(100) not null,
nivel varchar(100) not null,
activo int  not null
);
create table TRABAJADORHABILIDAD(
id_trabajador int unsigned ,
id_habilidad int unsigned,
activo int ,
PRIMARY KEY (id_trabajador,id_habilidad),
FOREIGN KEY (id_trabajador) references TRABAJADOR(id_trabajador) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (id_habilidad) references HABILIDAD(id_habilidad) ON UPDATE CASCADE ON DELETE CASCADE
);
create table VENTAS(
id_venta int unsigned auto_increment primary key,
id_trabajador int unsigned,
total_factura double,
activo int,
FOREIGN KEY (id_trabajador) references TRABAJADOR(id_trabajador) ON UPDATE CASCADE ON DELETE CASCADE
);
create table LINEAVENTA(
id_venta int unsigned ,
id_producto_final int unsigned,
cantidadVenta int,
precio_venta double,
activo int,
PRIMARY KEY (id_venta,id_producto_final),
foreign key (id_venta) references VENTAS(id_venta) ON UPDATE CASCADE ON DELETE CASCADE,
FOREIGN KEY (id_producto_final) references PRODUCTOENPLATAFORMA(id_producto_final) ON UPDATE CASCADE ON DELETE CASCADE
);

/*consultas*/
/*
select * from VIDEOJUEGO;
select * from PLATAFORMA;
select * from PRODUCTOENPLATAFORMA;	
select * from TRABAJADORHABILIDAD;
select * from HABILIDAD;
select * from TRABAJADOR;
select * from SUPERVISOR;
select * from VENDEDOR;
select * from LINEAVENTA;
select * from VENTAS;
select * from TRABAJADORHABILIDAD;

select * from PRODUCTO;
insert into PRODUCTO (nombre,marca,activo) values("gtaV","activision",1);
insert into PRODUCTOENPLATAFORMA (id_producto,id_plataforma,cantidad,precio_venta_actual,activo) values(31,1,30,40.50,1);
*/


