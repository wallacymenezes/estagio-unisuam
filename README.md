# Finanças Universitárias

Este projeto é uma aplicação completa (frontend e backend) para **controle de finanças pessoais de jovens universitários**, com foco na organização de **gastos, ganhos, investimentos e objetivos financeiros**. O sistema visa melhorar a saúde financeira do público universitário por meio do acompanhamento de seus dados e metas.

![Logo do Projeto](https://www.cdi24.com/wp-content/uploads/2019/10/Guide-to-hire-Java-Developer-in-2021.png "Developer Image")

---

## 🧠 Objetivo do Projeto

A proposta do sistema é **auxiliar universitários no controle de suas finanças pessoais**, promovendo educação financeira e autonomia sobre seus rendimentos. O sistema permite:

- Registro de **ganhos**, com distinção entre salário e outras fontes.
- Registro de **gastos**, separados entre fixos e temporários.
- Acompanhamento de **investimentos** (renda fixa e variável).
- Criação de **objetivos financeiros**, com prazo definido.
- Categorização dos gastos para facilitar o controle e análises.

---

## 🧩 Relacionamentos e Banco de Dados

O banco de dados foi modelado em **PostgreSQL**. A estrutura atual inclui as seguintes entidades:

- **Usuários**: Cada usuário possui seus próprios dados financeiros.
- **Ganhos**: Associados ao usuário; identificam se são salário.
- **Gastos**: Associados ao usuário e a uma categoria.
- **Investimentos**: Associados ao usuário.
- **Objetivos**: Metas financeiras com prazos definidos.
- **Categorias**: Utilizadas para classificar os gastos.

> Observação: Os campos podem ser alterados conforme novas funcionalidades forem implementadas, dependendo da disponibilidade de tempo.

---

## 🌐 ODS da ONU

O projeto contribui para os seguintes Objetivos de Desenvolvimento Sustentável:

- **ODS 1**: Erradicação da pobreza – ao ajudar no controle de finanças e evitar inadimplência.
- **ODS 4**: Educação de qualidade – promove a educação financeira entre jovens.
- **ODS 8**: Trabalho decente e crescimento econômico – incentiva o planejamento financeiro.
- **ODS 10**: Redução das desigualdades – contribui com inclusão financeira de estudantes vulneráveis.

---

## 📊 Dados sobre a Saúde Financeira dos Universitários

- **44%** dos estudantes endividados precisaram trancar seus cursos por falta de pagamento.
- **24%** relataram dificuldades de concentração devido a problemas financeiros.
- As principais causas são: desemprego (29%), outras prioridades financeiras (19%) e queda na renda (12%).  
  **Fonte**: [Jornal Metas](https://www.jornalmetas.com.br/geral/2025/01/2407751-dividas-levam-44-dos-estudantes-a-trancar-cursos-universitarios-no-brasil.html)

---

## ✅ Funcionalidades Previstas

1. **Autenticação e Autorização**
    - Cadastro e login de usuários
    - Proteção de rotas com JWT

2. **Módulos principais**
    - CRUD de ganhos
    - CRUD de gastos com categorias
    - CRUD de investimentos
    - CRUD de objetivos com prazo
    - Dashboard para visualização gráfica (futuramente)

3. **Possível melhoria futura**
    - Implementação de recurso de **família** para gestão compartilhada entre usuários.

---

## ⚙️ Tecnologias Utilizadas

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-000000.svg?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-6DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Spring Security](https://img.shields.io/badge/Spring%20Security-6DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
![TailwindCSS](https://img.shields.io/badge/TailwindCSS-38B2AC.svg?style=for-the-badge&logo=tailwind-css&logoColor=white)

---

## 🛠️ Ferramentas

![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
![VS Code](https://img.shields.io/badge/VSCode-007ACC.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white)
![Insomnia](https://img.shields.io/badge/Insomnia-black?style=for-the-badge&logo=insomnia&logoColor=5849BE)
![Render](https://img.shields.io/badge/Render-000000.svg?style=for-the-badge&logo=render&logoColor=white)

---

## 📅 Status do Projeto

- **Status**: Em desenvolvimento
- **Autor**: *[Seu Nome]*
- **Data de Início**: [dd/mm/aaaa]
- **Previsão de Conclusão**: [dd/mm/aaaa]

---

## 📂 Instruções de Configuração

1. Clone o repositório:
   ```bash
   git clone https://github.com/seu-usuario/nome-do-repositorio.git
