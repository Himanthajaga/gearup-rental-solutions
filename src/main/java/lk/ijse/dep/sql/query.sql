create database gearup;
use gearup;

create table admin(
                      a_id varchar(20) primary key,
                      a_name varchar(50),
                      a_password varchar(20)
);
insert into admin(a_id,a_name,a_password)values
                                             ("A001","jaga","1234"),
                                             ("A002","hima","12345"),
                                             ("A003","jgking","12346");


create table customer(
                         c_id varchar(20) primary key,
                         c_name varchar(50),
                         c_address varchar(50),
                         c_tel varchar(20),
                         a_id varchar(20),
                         foreign key (a_id) references admin(a_id) on delete cascade on update cascade
);

insert into customer(c_id,c_name,c_address,c_tel,a_id)values
                                                          ("C001","shaw","Galle","0771234567","A001"),
                                                          ("C002","sandu","Matara","0762345678","A001"),
                                                          ("C003","kavee","Galle","0701234523","A001");

create table payment(
                        p_id varchar(20) primary key,
                        p_amount decimal(10,2),
                        p_type varchar(20)
);
insert into payment(p_id,P_amount,P_type)values
                                             ("P001",5000.00,"cash"),
                                             ("P002",6000.00,"cash"),
                                             ("P003",7000.00,"card");
create table bokking(
                        b_id varchar(20) primary key,
                        b_date date,
                        c_id varchar(20),
                        p_id varchar(20),
                        foreign key (c_id) references customer(c_id) on delete cascade on update cascade,
                        foreign key (p_id) references payment (p_id) on delete cascade on update cascade
);

insert into bokking(b_id,b_date,c_id,p_id)values
                                              ("B001","2024-03-04","C001","P001"),
                                              ("B002","2024-03-05","C002","P002"),
                                              ("B003","2024-03-06","C003","P003");

create table machine(
                        m_id varchar(20) primary key,
                        m_name varchar(40),
                        m_desc varchar(40),
                        c_id varchar(20),
                        foreign key (c_id) references customer(c_id) on delete cascade on update cascade
);

insert into machine(m_id,m_name,m_desc,c_id)values
                                                ("M001","Wacker","Soil Compansion","C001"),
                                                ("M002","Grinder","Grinding things","C002"),
                                                ("M003","Zander","Zanding things","C003");

create table mechanic(
                         mec_id varchar(20) primary key,
                         mec_name varchar(40),
                         mec_address varchar(40),
                         mec_tel varchar(20),
                         mec_desc varchar(40),
                         mec_salary decimal(10,2)
);
insert into mechanic(mec_id,mec_name,mec_address,mec_tel,mec_desc,mec_salary)values
                                                                                 ("ME001","Sunil","Galle","0774567334","Repairing Wackers",10000.00),
                                                                                 ("ME002","Jayantha","Galle","0712344567","Repairing Grinders",12000.00),
                                                                                 ("ME003","Kumara","Galle","0786544325","Repairing Zanders",23000.00);

create table machine_mechanic_detail(
                                        m_id varchar(20),
                                        mec_id varchar(40),
                                        foreign key (m_id) references machine(m_id)on update cascade on delete cascade,
                                        foreign key (mec_id) references mechanic(mec_id) on update cascade on delete cascade
);

insert into machine_mechanic_detail(m_id,mec_id)values
                                                    ("M001","ME001"),
                                                    ("M002","ME002"),
                                                    ("M003","ME003");

create table reservation(
                            r_id varchar(20)primary key,
                            type varchar(20)
);

insert into reservation(r_id,type)values
                                      ("R001","Building Blocks"),
                                      ("R002","Sand"),
                                      ("R003","Rocks");

create table customer_reservation(
                                     r_id varchar(20),
                                     c_id varchar(20),
                                     date date
);
insert into customer_reservation(r_id,c_id,date)values
                                                    ("R001","C001","2023-03-06"),
                                                    ("R002","C002","2023-03-08"),
                                                    ("R003","C003","2023-03-09");

create table building_material(
                                  bm_id varchar(20)primary key,
                                  bm_desc varchar(20),
                                  bm_type varchar(20),
                                  bm_price decimal(10,2)
);

insert into building_material(bm_id,bm_desc,bm_type,bm_price)values
                                                                 ("BM001","Building Blocks","blocking",250.00),
                                                                 ("BM002","Sand","sanding",300.00),
                                                                 ("BM003","Rocks","hard rocks",400.00);

create table reservation_building_material(
                                              bm_id varchar(20),
                                              r_id varchar(20),
                                              date date,
                                              amount varchar(10),
                                              foreign key (bm_id)references building_material(bm_id)on update cascade on delete cascade,
                                              foreign key (r_id)references reservation(r_id)on update cascade on delete cascade
);

insert into reservation_building_material(bm_id,r_id,date,amount)values
                                                                     ("BM001","R001","2023-01-02","5000kg"),
                                                                     ("BM002","R002","2023-01-04","3000kg"),
                                                                     ("BM003","R003","2023-01-07","4000kg");

create table supplier(
                         s_id varchar(20)primary key,
                         s_name varchar(20),
                         s_address varchar(40),
                         s_tel varchar(12)
);

insert into supplier(s_id,s_name,s_address,s_tel)values
                                                     ("S001","Jagath","Galle","0787834467"),
                                                     ("S002","Nimal","Matara","0778733456"),
                                                     ("S003","Sunimal","Galle","0764577363");


create table supplier_building_material(
                                           bm_id varchar(20),
                                           s_id varchar(20),
                                           amount varchar(10),
                                           foreign key (bm_id)references building_material(bm_id)on update cascade on delete cascade,
                                           foreign key (s_id)references supplier(s_id)on update cascade on delete cascade
);

insert into supplier_building_material(bm_id,s_id,amount)values
                                                             ("BM001","S001","15000kg"),
                                                             ("BM002","S002","13000kg"),
                                                             ("BM003","S003","14000kg");