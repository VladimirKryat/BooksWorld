INSERT INTO public.userdata ( email, password, state, username) VALUES ( 'vladimirkryat@gmail.com', '$2a$08$o/exJCuLfeui7diaVWkCC.inVuJqgGzTzx/Av8VaYCrFH7CcJaWnK', 'ACTIVE', 'VladimirKryat');
INSERT INTO public.userdata ( email, password, state, username) VALUES ( 'yikic91458@terasd.com', '$2a$08$akLIRrE.xls9A.WgMcv5guHZHGqQfECJAaI0Jpvx.ort5fp5CczNm', 'ACTIVE', 'Djdfy');
INSERT INTO public.userdata ( email, password, state, username) VALUES ( 'jiknagelti@vusra.com', '$2a$08$mBP.WP1M0Mtcadr1U9LcQeYDCsHlm12OCMrv/p4A9TxxAaT3Nw9..', 'ACTIVE', 'Vov1234');

INSERT INTO public.user_role (user_id, roles) VALUES (1, 'USER');
INSERT INTO public.user_role (user_id, roles) VALUES (1, 'ADMIN');
INSERT INTO public.user_role (user_id, roles) VALUES (1, 'MANAGER');
INSERT INTO public.user_role (user_id, roles) VALUES (2, 'USER');
INSERT INTO public.user_role (user_id, roles) VALUES (3, 'USER');

INSERT INTO public.author ( name, birthday, date_of_death, biography) VALUES ( 'Джошуа Блох', '1961-08-28', null, 'Программный инженер и писатель, бывший работник компании Sun Microsystems и Google. Он возглавлял разработку и реализацию различных функционалов платформы Java, включая фреймворк коллекций Java Collections, пакет java.math и механизм assert.');
INSERT INTO public.author ( name, birthday, date_of_death, biography) VALUES ( 'А.С. Пушкин', '1799-05-26', '1837-01-29', 'Русский поэт, драматург и прозаик, заложивший основы русского реалистического направления, литературный критики теоретик литературы, историк, публицист, журналист; один из самых авторитетных литературных деятелей первой трети XIX века.');
INSERT INTO public.author ( name, birthday, date_of_death, biography) VALUES ( 'И. С. Тургенев', '1818-10-28', '1883-08-22', 'Иван Тургенев был одним из самых значимых русских писателей XIX века. Созданная им художественная система изменила поэтику романа как в России, так и за рубежом. Его произведения восхваляли и жестко критиковали, а Тургенев всю жизнь искал в них путь, который привел бы Россию к благополучию и процветанию.');
INSERT INTO public.author ( name, birthday, date_of_death, biography) VALUES ( 'Фредрик Бакман', '1981-06-20', null, 'Шведский писатель и блогер. Дебютировал в 2012 году, выпустив одновременно два романа: "Вторая жизнь Уве" и "Вещи, которые моему сыну следует знать о мире". Его книги переведены и издаются на более 25 языках мира');

INSERT INTO public.user_subscription_author (user_id, author_id) VALUES (1, 2);
INSERT INTO public.user_subscription_author (user_id, author_id) VALUES (1, 1);
INSERT INTO public.user_subscription_author (user_id, author_id) VALUES (2, 2);
INSERT INTO public.user_subscription_author (user_id, author_id) VALUES (3, 4);
INSERT INTO public.user_subscription_author (user_id, author_id) VALUES (1, 4);
INSERT INTO public.user_subscription_author (user_id, author_id) VALUES (3, 2);
INSERT INTO public.user_subscription_author (user_id, author_id) VALUES (3, 1);

INSERT INTO public.book (book_id, title, description, filename) VALUES (1, 'Effective Java', 'Книга представляет собой овеществленный опыт ее автора как программиста на Java. Новые возможности этого языка программирования, появившиеся в версиях, вышедших со времен предыдущего издания книги, по сути знаменуют появление совершенно новых концепций, так что для их эффективного использования недостаточно просто узнать об их существовании, и программировать на современном Java с использованием старых парадигм.', '4b4091d1-c435-4353-999f-6de462106a69.effective.jpg');
INSERT INTO public.book (book_id, title, description, filename) VALUES (2, 'У лукоморья дуб зеленый', 'Это замечательная книга для первого знакомства ребёнка с творчеством Александра Пушкина! В неё вошли отрывок из поэмы "Руслан и Людмила", "Сказка о рыбаке и рыбке", "Сказка о царе Салтане...", "Сказка о мёртвой царевне и о семи богатырях", "Сказка о золотом петушке", "Сказка о попе и о работнике его Балде" и "Песнь о вещем Олеге".', 'ed0be3d3-1c41-423d-8a36-58c873f5ee2c.luk.jpg');
INSERT INTO public.book (book_id, title, description, filename) VALUES (3, 'Капитанская дочка', 'Исторический роман «Капитанская дочка» А. С. Пушкина с иллюстрациями И. Миодушевского, С. Герасимова, Н. Каразина', '22481208-ffe9-457d-8149-ffcdf35e1888.cap.jpg');
INSERT INTO public.book (book_id, title, description, filename) VALUES (4, 'Отцы и дети', 'Роман И. С. Тургенева, написанный в 1860-1861 годах и опубликованный в 1862 году в журнале «Русский вестник». В обстановке «великих реформ» книга стала сенсацией и привлекла к себе всеобщее внимание.', 'e0287e10-5b46-465c-97fd-440288f7a383.fatherNchild.jpg');
INSERT INTO public.book (book_id, title, description, filename) VALUES (5, 'Вторая жизнь уве', 'На первый взгляд Уве – самый угрюмый человек на свете. Он, как и многие из нас, полагает, что его окружают преимущественно идиоты – соседи, которые неправильно паркуют свои машины; продавцы в магазине, говорящие на птичьем языке; бюрократы, портящие жизнь нормальным людям…Но у угрюмого ворчливого педанта – большое доброе сердце. И когда молодая семья новых соседей случайно повреждает его почтовый ящик, это становится началом невероятно трогательной истории об утраченной любви, неожиданной дружбе, бездомных котах и древнем искусстве сдавать назад на автомобиле с прицепом. Истории о том, как сильно жизнь одного человека может повлиять на жизни многих других.', '271d3b4d-0644-4287-be52-2f5fea9d565f.uve.png');

INSERT INTO public.genre_book (book_id, genre) VALUES (1, 'SCHOOLBOOK');
INSERT INTO public.genre_book (book_id, genre) VALUES (2, 'POETRY');
INSERT INTO public.genre_book (book_id, genre) VALUES (2, 'FAIRY_TALE');
INSERT INTO public.genre_book (book_id, genre) VALUES (2, 'CHILDREN');
INSERT INTO public.genre_book (book_id, genre) VALUES (3, 'ROMANCE');
INSERT INTO public.genre_book (book_id, genre) VALUES (4, 'ROMANCE');
INSERT INTO public.genre_book (book_id, genre) VALUES (5, 'ROMANCE');

INSERT INTO public.author_book (author_id, book_id) VALUES (1, 1);
INSERT INTO public.author_book (author_id, book_id) VALUES (2, 2);
INSERT INTO public.author_book (author_id, book_id) VALUES (2, 3);
INSERT INTO public.author_book (author_id, book_id) VALUES (3, 4);
INSERT INTO public.author_book (author_id, book_id) VALUES (4, 5);

INSERT INTO public.user_like_book (book_id, user_id) VALUES (2, 2);
INSERT INTO public.user_like_book (book_id, user_id) VALUES (3, 2);
INSERT INTO public.user_like_book (book_id, user_id) VALUES (2, 3);
INSERT INTO public.user_like_book (book_id, user_id) VALUES (5, 3);
INSERT INTO public.user_like_book (book_id, user_id) VALUES (3, 3);
INSERT INTO public.user_like_book (book_id, user_id) VALUES (5, 1);