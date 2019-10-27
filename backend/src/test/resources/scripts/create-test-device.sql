insert into hardware_dictionary(id, "value") values
    (1,'Karta graficzna'),
    (2,'Karta sieciowa'),
    (3,'Procesor');

insert into hardware(id, name, hardware_dictionary_id) VALUES
    (1,'GTX 1040',1),
    (2,'TP-Link',2),
    (3,'i5-7070',3),
    (4,'i3-5400',3),
    (5,'HD 7770',1);

insert into computer_sets(id, name) VALUES
    (1,'HP ProBook'),
    (2,'ACER Laptop');

insert into computer_sets_hardware(computer_set_id, hardware_id, valid_from, valid_to) VALUES
    (1,3,'2017-07-23',null),
    (1,1,'2018-09-28',null);