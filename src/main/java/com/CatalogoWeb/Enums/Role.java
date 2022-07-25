
package com.CatalogoWeb.Enums;


public enum Role {
    ADMIN, VENDEDOR, CLIENTE;
  
}

/*
ADMIN... PUEDE DAR PERFILES DE USUARIO....
VENDEDOR ... PUEDE CARGAR / MODIFICAR PRODUCTOS, VER REPORTES...
CLIENTE... PUEDE VER PROD Y COTIZARSE/CONSULTAR...
INVITADO... PUEDE VER PRODUCTOS PERO NO COTIZAR?????


El primer admin lo cargamos a mano...

roles:

ADMIN 
    puede modificar roles  -- esto en el header haciendo click en el usuario
    editar usuario -- esto en el header haciendo click en el usuario
    cargar productos
    ver catálogo
    emitir cotizaciones

VENDEDOR
    editar usuario -- esto en el header haciendo click en el usuario
    modificar
    cargar productos
    ver catálogo
    emitir cotizaciones

CLIENTE
    editar usuario -- esto en el header haciendo click en el usuario
    ver catálogo
    emitir cotizaciones

INVITADO (no logueado)
    ver catálogo


*/