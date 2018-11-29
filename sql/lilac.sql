set names utf8;
set foreign_key_checks = 0;

drop database if exists lilac;
create database if not exists lilac;
use lilac;

drop table if exists user_info;

create table user_info(
	id int not null primary key auto_increment comment "ID",
	user_id varchar(16) unique not null comment "ユーザーID",
	password varchar(16) not null comment "パスワード",
	family_name varchar(32) not null comment "姓",
	first_name varchar(32) not null comment "名",
	family_name_kana varchar(32) not null comment "姓かな",
	first_name_kana varchar(32) not null comment "名かな",
	sex tinyint default '0' not null comment "性別",
	email varchar(32) not null comment "メールアドレス",
	status tinyint default '0' not null comment "ステータス",
	logined tinyint default '0' not null comment "ログインフラグ",
	regist_date datetime not null comment "登録日",
	update_date datetime comment "更新日"
)
default charset=utf8
comment="会員情報テーブル"
;

insert into user_info values
(1,"guest","guest","インターノウス","ゲストユーザー","いんたーのうす","げすとゆーざー",0,"guest@gmail.com",0,0,now(),now()),
(2,"guest2","guest2","インターノウス","ゲストユーザー2","いんたーのうす","げすとゆーざー2",0,"guest2@gmail.com",0,0,now(),now()),
(3,"guest3","guest3","インターノウス","ゲストユーザー3","いんたーのうす","げすとゆーざー3",0,"guest3@gmail.com",0,0,now(),now()),
(4,"guest4","guest4","インターノウス","ゲストユーザー4","いんたーのうす","げすとゆーざー4",0,"guest4@gmail.com",0,0,now(),now()),
(5,"guest5","guest5","インターノウス","ゲストユーザー5","いんたーのうす","げすとゆーざー5",0,"guest5@gmail.com",0,0,now(),now()),
(6,"guest6","guest6","インターノウス","ゲストユーザー6","いんたーのうす","げすとゆーざー6",0,"guest6@gmail.com",0,0,now(),now()),
(7,"guest7","guest7","インターノウス","ゲストユーザー7","いんたーのうす","げすとゆーざー7",0,"guest7@gmail.com",0,0,now(),now()),
(8,"guest8","guest8","インターノウス","ゲストユーザー8","いんたーのうす","げすとゆーざー8",0,"guest8@gmail.com",0,0,now(),now()),
(9,"guest9","guest9","インターノウス","ゲストユーザー9","いんたーのうす","げすとゆーざー9",0,"guest9@gmail.com",0,0,now(),now()),
(10,"guest10","guest10","インターノウス","ゲストユーザー10","いんたーのうす","げすとゆーざー10",0,"guest10@gmail.com",0,0,now(),now()),
(11,"guest11","guest11","インターノウス","ゲストユーザー11","いんたーのうす","げすとゆーざー11",0,"guest11@gmail.com",0,0,now(),now()),
(12,"guest12","guest12","インターノウス","ゲストユーザー12","いんたーのうす","げすとゆーざー12",0,"guest12@gmail.com",0,0,now(),now())
;


drop table if exists product_info;

create table product_info(
	id int not null primary key auto_increment comment "ID",
	product_id int unique not null comment "商品ID",
	product_name varchar(100) unique not null comment "商品名",
	product_name_kana varchar(100) unique not null comment "商品名かな",
	product_description varchar(255) not null comment "商品詳細",
	category_id int not null comment "カテゴリID",
	price int comment "価格",
	image_file_path varchar(100) comment "画像ファイルパス",
	image_file_name varchar(50) comment "画像ファイル名",
	release_date datetime not null comment "発売年月",
	release_company varchar(50) comment "発売会社",
	status tinyint default '0' not null comment "ステータス",
	regist_date datetime not null comment "登録日",
	update_date datetime comment "更新日",
	foreign key(category_id) references m_category(category_id)
)
default charset=utf8
comment="商品情報テーブル"
;

insert into product_info values
( 1, 1,"ブルーベリーパイ","ぶるーべりーぱい","ブルーベリーパイの商品詳細",2,200,"./images","pai1.jpg",now(),"発売会社",0,now(),now()),
( 2, 2,"苺パイ","いちごぱい","苺パイの商品詳細",2,300,"./images","pai2.jpg",now(),"発売会社",0,now(),now()),
( 3, 3,"ミックスベリーパイ","みっくすべりーぱい","ミックスベリーパイの商品詳細",2,400,"./images","pai3.jpg",now(),"発売会社",0,now(),now()),
( 4, 4,"キッシュ","きっしゅ","キッシュの商品詳細",2,300,"./images","pai4.jpg",now(),"発売会社",0,now(),now()),
( 5, 5,"りんごパイ","りんごぱい","りんごパイの商品詳細",2,350,"./images","pai5.jpg",now(),"発売会社",0,now(),now()),
( 6, 6,"ボリュームサンド","ぼりゅーむさんど","ボリュームサンドの商品詳細",3,250,"./images","sandwich1.jpg",now(),"発売会社",0,now(),now()),
( 7, 7,"たまごサンド","たまごさんど","たまごサンドの商品詳細",3,150,"./images","sandwich2.jpg",now(),"発売会社",0,now(),now()),
( 8, 8,"苺サンド","いちごさんど","苺サンドの商品詳細",3,300,"./images","sandwich3.jpg",now(),"発売会社",0,now(),now()),
( 9, 9,"かつサンド","かつさんど","かつサンドの商品詳細",3,500,"./images","sandwich4.jpg",now(),"発売会社",0,now(),now()),
( 10, 10,"アボカドサンド","あぼかどさんど","アボカドサンドの商品詳細",3,250,"./images","sandwich5.jpg",now(),"発売会社",0,now(),now()),
( 11, 11,"プレーンベーグル","ぷれーんべーぐる","プレーンベーグルの商品詳細",4,120,"./images","bagle1.jpg",now(),"発売会社",0,now(),now()),
( 12, 12,"チョコベーグル","ちょこべーぐる","チョコベーグルの商品詳細",4,200,"./images","bagle2.jpg",now(),"発売会社",0,now(),now()),
( 13, 13,"桜ベーグル","さくらべーぐる","桜ベーグルの商品詳細",4,250,"./images","bagle3.jpg",now(),"発売会社",0,now(),now()),
( 14, 14,"ブルーベリーベーグル","ぶるーべりーべーぐる","ブルーベリーベーグルの商品詳細",4,300,"./images","bagle4.jpg",now(),"発売会社",0,now(),now()),
( 15, 15,"胡麻ベーグル","ごまべーぐる","胡麻ベーグルの商品詳細",4,250,"./images","bagle5.jpg",now(),"発売会社",0,now(),now()),
( 16, 16,"シナモンロール","しなもんろーる","シナモンロールの商品詳細",5,150,"./images","bun1.jpg",now(),"発売会社",0,now(),now()),
( 17, 17,"あんぱん","あんぱん","あんぱんの商品詳細",5,120,"./images","bun2.jpg",now(),"発売会社",0,now(),now()),
( 18, 18,"チョコクロワッサン","ちょこくろわっさん","チョコクロワッサンの商品詳細",5,150,"./images","bun3.jpg",now(),"発売会社",0,now(),now()),
( 19, 19,"ドーナツ","どーなつ","ドーナツの商品詳細",5,100,"./images","bun4.jpg",now(),"発売会社",0,now(),now()),
( 20, 20,"シュトレン","しゅとれん","シュトレンの商品詳細",5,300,"./images","bun5.jpg",now(),"発売会社",0,now(),now())
;


drop table if exists cart_info;

create table cart_info(
	id int not null primary key auto_increment comment "ID",
	user_id varchar(16) not null comment "ユーザーID",
	temp_user_id varchar(16) comment "仮ユーザーID",
	product_id int not null comment "商品ID",
	product_count int not null comment "個数",
	price int not null comment "金額",
	regist_date datetime not null comment "登録日",
	update_date datetime comment "更新日"
)
default charset=utf8
comment="カート情報テーブル"
;


drop table if exists purchase_history_info;

create table purchase_history_info(
	id int not null primary key auto_increment comment "ID",
	user_id varchar(16) not null comment "ユーザーID",
	product_id int not null comment "商品ID",
	product_count int not null comment "個数",
	price int not null comment "金額",
	destination_id int not null comment "宛先情報ID",
	regist_date datetime not null comment "登録日",
	update_date datetime comment "更新日",
	foreign key(user_id) references user_info(user_id),
	foreign key(product_id) references product_info(product_id)
)
default charset=utf8
comment="購入履歴情報テーブル"
;

drop table if exists destination_info;

create table destination_info(
	id int not null primary key auto_increment comment "ID",
	user_id varchar(16) not null comment "ユーザーID",
	family_name varchar(32) not null comment "姓",
	first_name varchar(32) not null comment "名",
	family_name_kana varchar(32) not null comment "姓かな",
	first_name_kana varchar(32) not null comment "名かな",
	email varchar(32) not null comment "メールアドレス",
	tel_number varchar(13) not null comment "電話番号",
	user_address varchar(50) not null comment "住所",
	regist_date datetime not null comment "登録日",
	update_date datetime comment "更新日"
)
default charset=utf8
comment="宛先情報テーブル"
;

insert into destination_info values
(1,"guest","インターノウス","テストユーザー","いんたーのうす","てすとゆーざー","guest@internous.co.jp","080-1234-5678","東京都千代田区三番町１ー１　ＫＹ三番町ビル１Ｆ",now(),now())
;


drop table if exists m_category;

create table m_category(
	id int not null primary key comment "ID",
	category_id int unique not null comment "カテゴリID",
	category_name varchar(20) unique not null comment "カテゴリ名",
	category_description varchar(100) comment "カテゴリ詳細",
	insert_date datetime not null comment "登録日",
	update_date datetime comment "更新日"
)
default charset=utf8
comment="カテゴリマスタテーブル"
;

insert into m_category values
(1,1,"全てのカテゴリー","パイ、サンドイッチ、ベーグル、菓子パン全てのカテゴリーが対象となります",now(), now()),
(2,2,"パイ","パイに関するカテゴリーが対象となります",now(),now()),
(3,3,"サンドイッチ","サンドイッチに関するカテゴリーが対象となります",now(),now()),
(4,4,"ベーグル","ベーグルに関するカテゴリーが対象となります",now(),now()),
(5,5,"菓子パン","菓子パンに関するカテゴリーが対象となります",now(),now());