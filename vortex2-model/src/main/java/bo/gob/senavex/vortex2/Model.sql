-- public.cli_empresa definition

-- Drop table

-- DROP TABLE public.cli_empresa;

CREATE TABLE public.cli_empresa (
	id_empresa bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	descripcion varchar(255) NULL,
	vf_estado varchar(255) NULL,
	nit varchar(255) NULL,
	nombre_comercial varchar(255) NULL,
	razon_social varchar(255) NULL,
	vd_tipo varchar(255) NULL,
	CONSTRAINT cli_empresa_pkey PRIMARY KEY (id_empresa)
);


-- public.par_dominio definition

-- Drop table

-- DROP TABLE public.par_dominio;

CREATE TABLE public.par_dominio (
	id_dominio bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	codigo varchar(255) NULL,
	vf_estado varchar(255) NULL,
	nombre_campo varchar(255) NULL,
	nombre_tabla varchar(255) NULL,
	CONSTRAINT par_dominio_pkey PRIMARY KEY (id_dominio)
);


-- public.seg_persona definition

-- Drop table

-- DROP TABLE public.seg_persona;

CREATE TABLE public.seg_persona (
	id_persona bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	apellido_materno varchar(255) NULL,
	apellido_paterno varchar(255) NULL,
	complemento_doc varchar(255) NULL,
	direccion varchar(255) NULL,
	vf_estado varchar(255) NULL,
	expedicion_doc varchar(255) NULL,
	fecha_nacimiento timestamp NULL,
	vd_genero varchar(255) NULL,
	nombres varchar(255) NULL,
	numero_celular varchar(255) NULL,
	numero_doc int8 NULL,
	numero_telefonico varchar(255) NULL,
	vd_tipo_doc varchar(255) NULL,
	CONSTRAINT seg_persona_pkey PRIMARY KEY (id_persona)
);


-- public.cli_categoria definition

-- Drop table

-- DROP TABLE public.cli_categoria;

CREATE TABLE public.cli_categoria (
	id_categoria bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	vd_activos_ufv varchar(255) NULL,
	vd_categoria varchar(255) NULL,
	vf_estado varchar(255) NULL,
	vd_exportaciones_rubros varchar(255) NULL,
	vd_exportaciones_ufv varchar(255) NULL,
	fecha timestamp NULL,
	vd_importaciones_rubros varchar(255) NULL,
	vd_importaciones_ufv varchar(255) NULL,
	vd_nro_empleados varchar(255) NULL,
	vd_ventas_ufv varchar(255) NULL,
	id_empresa int8 NULL,
	CONSTRAINT cli_categoria_pkey PRIMARY KEY (id_categoria),
	CONSTRAINT fkhr5wnxtsigbniqhfe4q2gc5bp FOREIGN KEY (id_empresa) REFERENCES cli_empresa(id_empresa)
);


-- public.cli_contacto definition

-- Drop table

-- DROP TABLE public.cli_contacto;

CREATE TABLE public.cli_contacto (
	id_contacto bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	descripcion varchar(255) NULL,
	vf_estado varchar(255) NULL,
	fecha_verficacion timestamp NULL,
	nombre varchar(255) NULL,
	vd_tipo varchar(255) NULL,
	valor varchar(255) NULL,
	id_empresa int8 NULL,
	CONSTRAINT cli_contacto_pkey PRIMARY KEY (id_contacto),
	CONSTRAINT fkpx8hfdchwg2mqe59rs6rcgsws FOREIGN KEY (id_empresa) REFERENCES cli_empresa(id_empresa)
);


-- public.cli_direccion definition

-- Drop table

-- DROP TABLE public.cli_direccion;

CREATE TABLE public.cli_direccion (
	id_direccion bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	avenida int8 NULL,
	calles varchar(255) NULL,
	vd_departamento varchar(255) NULL,
	domicilio varchar(255) NULL,
	edificio varchar(255) NULL,
	vf_estado varchar(255) NULL,
	fecha_fin timestamp NULL,
	fecha_inicio timestamp NULL,
	geo_referencia varchar(255) NULL,
	vd_municipio varchar(255) NULL,
	nombre varchar(255) NULL,
	oficina varchar(255) NULL,
	piso int8 NULL,
	referencia varchar(255) NULL,
	vd_tipo varchar(255) NULL,
	id_empresa int8 NULL,
	CONSTRAINT cli_direccion_pkey PRIMARY KEY (id_direccion),
	CONSTRAINT fkrltfnemyeri6wfrywoy7tisl FOREIGN KEY (id_empresa) REFERENCES cli_empresa(id_empresa)
);


-- public.cli_documento definition

-- Drop table

-- DROP TABLE public.cli_documento;

CREATE TABLE public.cli_documento (
	id_documento bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	codigo_verificacion varchar(255) NULL,
	document_respaldo varchar(255) NULL,
	vf_estado varchar(255) NULL,
	fecha_verificacion timestamp NULL,
	numero varchar(255) NULL,
	observaciones varchar(255) NULL,
	vd_tipo varchar(255) NULL,
	id_empresa int8 NULL,
	CONSTRAINT cli_documento_pkey PRIMARY KEY (id_documento),
	CONSTRAINT fkf308evxlwtsp5faiqanjibk3c FOREIGN KEY (id_empresa) REFERENCES cli_empresa(id_empresa)
);


-- public.CTA_pago definition

-- Drop table

-- DROP TABLE public.CTA_pago;

CREATE TABLE public.CTA_pago (
	id_pago bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	descripcion varchar(255) NULL,
	vf_estado varchar(255) NULL,
	fecha_aceptacion timestamp NULL,
	fecha_registro timestamp NULL,
	id_referencia varchar(255) NULL,
	monto varchar(255) NULL,
	referencia varchar(255) NULL,
	vd_tipo varchar(255) NULL,
	id_empresa int8 NULL,
	CONSTRAINT CTA_pago_pkey PRIMARY KEY (id_pago),
	CONSTRAINT fkomywh5fnmrx44twlpo65ghfhw FOREIGN KEY (id_empresa) REFERENCES cli_empresa(id_empresa)
);


-- public.par_valor definition

-- Drop table

-- DROP TABLE public.par_valor;

CREATE TABLE public.par_valor (
	id_valor bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	descripcion varchar(255) NULL,
	vf_estado varchar(255) NULL,
	etiqueta varchar(255) NULL,
	orden varchar(255) NULL,
	padre varchar(255) NULL,
	valor varchar(255) NULL,
	id_dominio int8 NULL,
	CONSTRAINT par_valor_pkey PRIMARY KEY (id_valor),
	CONSTRAINT fk7n62ymp9giol803ppkwujbf9v FOREIGN KEY (id_dominio) REFERENCES par_dominio(id_dominio)
);


-- public.reg_registro definition

-- Drop table

-- DROP TABLE public.reg_registro;

CREATE TABLE public.reg_registro (
	id_registro bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	codigo_ruex varchar(255) NULL,
	codigo_rui varchar(255) NULL,
	descripcion varchar(255) NULL,
	vf_estado varchar(255) NULL,
	fecha_emision timestamp NULL,
	fecha_vencimiento timestamp NULL,
	vd_tipo varchar(255) NULL,
	id_empresa int8 NULL,
	CONSTRAINT reg_registro_pkey PRIMARY KEY (id_registro),
	CONSTRAINT fkfyqb0417dy0wp7vk0plyc5itn FOREIGN KEY (id_empresa) REFERENCES cli_empresa(id_empresa)
);


-- public.seg_usuario definition

-- Drop table

-- DROP TABLE public.seg_usuario;

CREATE TABLE public.seg_usuario (
	id_usuario bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	clave_acceso varchar(255) NULL,
	correo_electronico varchar(255) NULL,
	vf_estado varchar(255) NULL,
	vd_tipo varchar(255) NULL,
	id_persona int8 NULL,
	CONSTRAINT seg_usuario_pkey PRIMARY KEY (id_usuario),
	CONSTRAINT fkh65vf2kggga6odrdppxgbpu86 FOREIGN KEY (id_persona) REFERENCES seg_persona(id_persona)
);


-- public.CTA_deposito definition

-- Drop table

-- DROP TABLE public.CTA_deposito;

CREATE TABLE public.CTA_deposito (
	id_deposito bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	comprobante varchar(255) NULL,
	vd_cuenta varchar(255) NULL,
	descripcion varchar(255) NULL,
	documento varchar(255) NULL,
	vf_estado varchar(255) NULL,
	fecha_deposito timestamp NULL,
	fecha_verificacion timestamp NULL,
	monto float8 NULL,
	observaciones varchar(255) NULL,
	id_pago int8 NULL,
	CONSTRAINT CTA_deposito_pkey PRIMARY KEY (id_deposito),
	CONSTRAINT fkri3em4g2yfnkxsr9qdo86wex2 FOREIGN KEY (id_pago) REFERENCES CTA_pago(id_pago)
);


-- public.log_sesion definition

-- Drop table

-- DROP TABLE public.log_sesion;

CREATE TABLE public.log_sesion (
	id_sesion bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	vf_estado varchar(255) NULL,
	fecha_login timestamp NULL,
	fecha_logout timestamp NULL,
	id_usuario int8 NULL,
	CONSTRAINT log_sesion_pkey PRIMARY KEY (id_sesion),
	CONSTRAINT fkm7c7sxxnye0sbr83g5ck5thra FOREIGN KEY (id_usuario) REFERENCES seg_usuario(id_usuario)
);


-- public.reg_seguimiento definition

-- Drop table

-- DROP TABLE public.reg_seguimiento;

CREATE TABLE public.reg_seguimiento (
	id_seguimiento bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	vf_estado varchar(255) NULL,
	id_referencia varchar(255) NULL,
	referencia varchar(255) NULL,
	vd_tipo varchar(255) NULL,
	id_usuario int8 NULL,
	CONSTRAINT reg_seguimiento_pkey PRIMARY KEY (id_seguimiento),
	CONSTRAINT fko9uvrc8m6qj38wed25ytyqhkc FOREIGN KEY (id_usuario) REFERENCES seg_usuario(id_usuario)
);


-- public.seg_operador definition

-- Drop table

-- DROP TABLE public.seg_operador;

CREATE TABLE public.seg_operador (
	id_operador bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	vd_cargo varchar(255) NULL,
	vf_estado varchar(255) NULL,
	fecha_desde timestamp NULL,
	fecha_hasta timestamp NULL,
	fecha_respuesta timestamp NULL,
	fecha_solicitud timestamp NULL,
	id_empresa int8 NULL,
	id_usuario int8 NULL,
	CONSTRAINT seg_operador_pkey PRIMARY KEY (id_operador),
	CONSTRAINT fk6pwwx9ivql04sms2ya8ki5smo FOREIGN KEY (id_usuario) REFERENCES seg_usuario(id_usuario),
	CONSTRAINT fkbn746aoxy41hh1818w7ygnpe1 FOREIGN KEY (id_empresa) REFERENCES cli_empresa(id_empresa)
);


-- public.log_evento definition

-- Drop table

-- DROP TABLE public.log_evento;

CREATE TABLE public.log_evento (
	id_evento bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	fecha timestamp NULL,
	operacion varchar(255) NULL,
	id_sesion int8 NULL,
	CONSTRAINT log_evento_pkey PRIMARY KEY (id_evento),
	CONSTRAINT fkcvy2ef6j94egbaxytlw1wq9ys FOREIGN KEY (id_sesion) REFERENCES log_sesion(id_sesion)
);


-- public.log_cambio definition

-- Drop table

-- DROP TABLE public.log_cambio;

CREATE TABLE public.log_cambio (
	id_cambio bigserial NOT NULL,
	aud_updated_at timestamp NOT NULL,
	aud_updated_by varchar(255) NOT NULL,
	aud_version int8 NOT NULL,
	nombre_columna varchar(255) NULL,
	nombre_tabla varchar(255) NULL,
	nuevo_valor varchar(255) NULL,
	valor varchar(255) NULL,
	id_evento int8 NULL,
	CONSTRAINT log_cambio_pkey PRIMARY KEY (id_cambio),
	CONSTRAINT fk7k8fpmangg2bf8urtai2b239q FOREIGN KEY (id_evento) REFERENCES log_evento(id_evento)
);