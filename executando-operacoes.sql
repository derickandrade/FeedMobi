-- apresenta view
SELECT * FROM Relatorio_Geral_Avaliacoes;

-- chama procedure para ver a media
CALL Media_Nota();

-- chama procedure para ver historico de um motorista
CALL Historico_Motorista('42859249605');

