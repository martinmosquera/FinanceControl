# FinanceControl
* Trabalho 1 - Desenvolvimento para Dispositivos Móveis
* Professor: Alessandro Brawerman
---

Uma FinTech, startup da área de finanças, solicitou que fosse desenvolvido um aplicativo para Android, que auxiliasse o usuário em sua vida financeira. O aplicativo tem por objetivo permitir o cadastro de todos os gastos (débitos) e ganhos (créditos) financeiros do usuário, auxiliando o mesmo na classificação dos gastos e na organização de sua vida financeira.
Para este aplicativo a FinTech pede como requisito a capacidade de gravar permanentemente os dados, em um banco de dados SQLite local ao dispositivo, e que seja desenvolvida uma interface elegante, simples e limpa.

---

* Inclua no projeto um ícone para seu aplicativo que representa a FinTech que contratou sua equipe. O ícone deve ser fornecido junto ao projeto para que o mesmo seja instalado no dispositivo.

* Tela de apresentação do app que permite ao desenvolvedor mostrar sua marca, deve ser mostrada por apenas 3 segundos. Mostre a logo da FinTech que você está representando.


## Activity Principal - Dashboard

* A activity principal deve apresentar um dashboard. Ela possui uma view com botões
(distribuídos de maneira bem equilibrada na interface) que devem levar o usuário a poder
executar as ações do aplicativo, sendo elas: cadastro de operações; extrato; pesquisar; lista
classificada; e sair (fecha o aplicativo).

## Activity Cadastro de operações

* Deve permitir ao usuário cadastrar novas operações financeiras. O usuário deve marcar a classificação da operação de acordo com os filtros do app (abaixo), informar o valor e data.
* Filtros para operações de débito – moradia, saúde, outros.
* Filtros para operações de crédito – salário, outros.
* Estes registros devem ser gravados em banco de dados local.

## Activity Extrato

* Extrato fixo das últimas 15 operações cronológicas, ou seja, de acordo com a data. Deve
mostrar em uma lista as 15 últimas operações (mostrar na célula classificação, data e valor) e
informar o saldo atual da conta do usuário (total de créditos – total de débitos).

## Activity Pesquisar

* Deve permitir que o usuário pesquise operações financeiras cadastradas informando o
período de início e fim (data). Deve ainda permitir que o usuário filtre a resposta de acordo com as opções - todas as operações, débito ou crédito. A informação é mostrada em uma lista, sendo que na célula deve-se mostrar classificação, data e valor.

## Activity Lista Classificada

* Apresenta em uma lista cada categoria de débito e crédito, com o valor gasto ou recebido até o momento. A lista deve iniciar com as categorias de débito e passar para as de crédito.

## Sair

* Fecha o aplicativo.