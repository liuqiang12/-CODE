/*
这里是这个 SQL 文件的注释，你随便怎么写
*/
/* sql1 */
DROP TABLE t_abc
/* 你可以随便写任何的注释文字，只有距离 SQL 语句最近的那一行注释，才会被认为是键值 */
/* getpet*/
SELECT * FROM t_pet WHERE id=@id
/* listpets*/
SELECT * FROM t_pet $condition