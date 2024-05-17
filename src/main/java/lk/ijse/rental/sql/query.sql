create database gearup;
use gearup;

create table admin(
                      a_id varchar(20) primary key,
                      a_name varchar(50),
                      a_password varchar(20),
                      a_confirmPasword varchar(20),
                      a_email varchar(50)
);
insert into admin(a_id,a_name,a_password,a_confirmPasword,a_email)values
                                                                      ("A001","jaga","1234","1234","himanthagamachchige@gmail.com"),
                                                                      ("A002","hima","12345","12345","hima123@gmail.com"),
                                                                      ("A003","jgking","12346","12346","jgkingjaga@gmail.com");


create table customer(
                         c_email varchar(50) primary key,
                         c_name varchar(50),
                         c_address varchar(50),
                         c_tel varchar(20),
                         c_id varchar(20)
);

insert into customer(c_email,c_name,c_address,c_tel,c_id)values
                                                             ("shaw123@gmail.com","shaw","Galle","0771234567","C001"),
                                                             ("sandu123@gmail.com","sandu","Matara","0762345678","C002"),
                                                             ("kavee123@gmail.com","kavee","Galle","0701234523","C003");
create table supplier(
                         s_email varchar(20)primary key,
                         s_name varchar(20),
                         s_address varchar(40),
                         s_tel varchar(12),
                         s_id varchar(20)

);

insert into supplier(s_email,s_name,s_address,s_tel,s_id)values
                                                             ("jagath@gmail.com","Jagath","Galle","0787834467","S001"),
                                                             ("nimal@gmail.com","Nimal","Matara","0778733456","S002"),
                                                             ("sunimal@gmail.com","Sunimal","Galle","0764577363","S003");

create table payment(
                        p_id varchar(20) primary key,
                        p_type varchar(20),
                        s_email varchar(20),
                        p_amount decimal(10,2),
                        foreign key (s_email) references supplier(s_email)on delete cascade on update cascade
);
insert into payment(p_id,P_type,s_email,P_amount)values
                                                     ("P001","cash","jagath@gmail.com",5000.00),
                                                     ("P002","cash","nimal@gmail.com",6000.00),
                                                     ("P003","card","sunimal@gmail.com",7000.00);

create table machine(
                        m_id varchar(20) primary key,
                        m_name varchar(40),
                        m_desc varchar(40),
                        m_rental_price decimal(10,2),
                        isAvailable int(1),
                        c_email varchar(50),
                        foreign key (c_email) references customer(c_email) on delete cascade on update cascade
);

insert into machine(m_id,m_name,m_desc,m_rental_price,isAvailable,c_email)values
                                                                              ("M001","Wacker","Soil Compansion",4500.00,1,"shaw123@gmail.com"),
                                                                              ("M002","Grinder","Grinding things",500.00,1,"sandu123@gmail.com"),
                                                                              ("M003","Zander","Zanding things",500.00,1,"kavee123@gmail.com");
create table bokking(
                        b_id varchar(20) primary key,
                        b_date date,
                        c_email varchar(50),
                        m_id varchar(20),
                        foreign key (c_email) references customer(c_email) on delete cascade on update cascade,
                        foreign key (m_id) references machine (m_id) on delete cascade on update cascade
);

insert into bokking(b_id,b_date,c_email,m_id)values
                                                 ("B001","2024-03-04","shaw123@gmail.com","M001"),
                                                 ("B002","2024-03-05","sandu123@gmail.com","M002"),
                                                 ("B003","2024-03-06","kavee123@gmail.com","M003");

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
                                        mmd_id varchar(30) primary key,
                                        m_id varchar(20),
                                        mec_id varchar(40),
                                        foreign key (m_id) references machine(m_id)on update cascade on delete cascade,
                                        foreign key (mec_id) references mechanic(mec_id) on update cascade on delete cascade
);

insert into machine_mechanic_detail(mmd_id,m_id,mec_id)values
                                                           ("MM001","M001","ME001"),
                                                           ("MM002","M002","ME002"),
                                                           ("MM003","M003","ME003");

create table reservation(
                            r_id varchar(20)primary key,
                            r_type varchar(20)
);

insert into reservation(r_id,r_type)values
                                        ("R001","Building Blocks"),
                                        ("R002","Sand"),
                                        ("R003","Rocks");

create table customer_reservation(
                                     r_id varchar(20),
                                     c_email varchar(50),
                                     date date,
                                     foreign key (c_email)references customer(c_email)on delete cascade on update cascade
);
insert into customer_reservation(r_id,c_email,date)values
                                                       ("R001","shaw123@gmail.com","2023-03-06"),
                                                       ("R002","sandu123@gmail.com","2023-03-08"),
                                                       ("R003","kavee123@gmail.com","2023-03-09");

create table building_material(
                                  bm_id varchar(20)primary key,
                                  bm_desc varchar(20),
                                  bm_type varchar(20),
                                  bm_price decimal(10,2),
                                  bm_amount int(20),
                                  s_email varchar(20),
                                  foreign key (s_email)references supplier(s_email)on delete cascade on update cascade
);

insert into building_material(bm_id,bm_desc,bm_type,bm_price,bm_amount,s_email)values
                                                                                   ("BM001","Building Blocks","blocking",100.00,10000,"jagath@gmail.com"),
                                                                                   ("BM002","Cement","Concrete works",2000.00,300,"nimal@gmail.com");


create table reservation_building_material(
                                              bm_id varchar(20),
                                              r_id varchar(20),
                                              date date,
                                              amount varchar(10),
                                              foreign key (bm_id)references building_material(bm_id)on update cascade on delete cascade,
                                              foreign key (r_id)references reservation(r_id)on update cascade on delete cascade
);

insert into reservation_building_material(bm_id,r_id,date,amount)values
                                                                     ("BM001","R001","2023-01-02",100),
                                                                     ("BM002","R002","2023-01-04",10);



create table supplier_building_material(
                                           bm_id varchar(20),
                                           s_email varchar(20),
                                           amount varchar(10),
                                           bm_desc  varchar(20),
                                           foreign key (bm_id)references building_material(bm_id)on update cascade on delete cascade,
                                           foreign key (s_email)references supplier(s_email)on update cascade on delete cascade
);

insert into supplier_building_material(bm_id,s_email,amount,bm_desc)values
                                                                        ("BM001","jagath@gmail.com",10000,"Building Blocks"),
                                                                        ("BM002","nimal@gmail.com",300,"cement");

create table rent_order(
                           o_id varchar(20) primary key,
                           o_date date,
                           r_date date,
                           c_email varchar(20),
                           r_total decimal(10,2),

                           foreign key (c_email) references customer(c_email)on update cascade on delete cascade

);
insert into rent_order(o_id,o_date,r_date,c_email,r_total)values
                                                              ("O001","2024-03-02","2024-03-03","shaw123@gmail.com",2000.00),
                                                              ("O002","2024-03-03","2024-03-04","sandu123@gmail.com",3000.00),
                                                              ("O003","2024-03-04","2024-03-05","kavee123@gmail.com",4000.00);


create table order_detail(
                             o_id varchar(20),
                             m_id varchar(20),
                             foreign key (o_id) references rent_order(o_id)on update cascade on delete cascade,
                             foreign key (m_id) references machine(m_id)on update cascade on delete cascade
);
CREATE TABLE sell (
                      se_id VARCHAR(20) PRIMARY KEY,
                      se_date DATE,
                      c_email VARCHAR(20),
                      s_total DECIMAL(10,2),
                      FOREIGN KEY (c_email) REFERENCES customer(c_email) ON UPDATE CASCADE ON DELETE CASCADE
);

-- Insert data into the 'sell' table
INSERT INTO sell (se_id, se_date, c_email, s_total) VALUES
                                                        ("S001", "2024-03-23", "shaw123@gmail.com", 2000.00),
                                                        ("S002", "2024-03-11", "sandu123@gmail.com", 3000.00),
                                                        ("S003", "2024-03-04", "kavee123@gmail.com", 4000.00);
create table sell_material(
                              se_id varchar(20),
                              bm_id varchar(20),
                              bm_unit_price decimal(10,2),
                              bm_qty int (10),
                              foreign key (se_id) references sell(se_id)on update cascade on delete cascade,
                              foreign key (bm_id) references building_material(bm_id)on update cascade on delete cascade
);