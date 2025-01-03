# API de Receitas e Ingredientes

## Problema:
Fui ao mercado e trouxe ovos, leite, farinha e carne. Gostaria de uma api onde eu soubesse de forma rápida e fácil o que posso fazer com os ingredientes que tenho.
   ### Documentação da api via Swagger disponivel online e acessivel via web:  
       https://heroic-upliftment-production.up.railway.app/api/v1/swagger-ui/index.html
## Tecnologias Utilizadas 
 **Backend:**
 - Java 17 Framework Spring
 - Spring Data Jpa e Hibernate(Persistência de Dados)
 - Flyway (Para Scripts de migração do banco)
 - Lombok (redução do codigo boilerplate)
 - Banco de Dados: PostgreSQL (hospedado no Railway) e H2 (para os testes)
 - CI/CD: GitHub Actions
 - Deploy: Railway (deploy automático usando Docker)
 - Containerização: Docker (Imagem hospedada no DockerHub)
 - Swagger: Documentação interativa da API


## Diagrama de classes UML
![Untitled Diagram-Page-1 (4)](https://github.com/user-attachments/assets/a31a0513-7025-4a59-b945-9b9928a52daa)

## Funcionalidades
- Cadastro de Ingredientes: Permite adicionar novos ingredientes.
- Cadastro de Receitas: Permite criar receitas com a associação de ingredientes.
- Edição de Ingredientes e Receitas: Permite editar ingredientes e receitas já cadastradas.
- Listagem de Receitas: Exibe receitas que incluem os ingredientes fornecidos, com destaque para as receitas 100% compatíveis (que contêm todos os ingredientes informados).
## Como Executar?

### Ferramentas necessárias:  
 - JDK 17
 - Docker
 - Ide de sua preferência.

Para rodar a API localmente, siga os passos abaixo:

1. **Clone o repositório**:
   ```bash
   [git clone https://github.com/usuario/minha-api.git](https://github.com/MateusLovesJavaCoffee/RecipeHub.git)
2. **Subir o banco de dados Postgresql**:
   ```bash
   docker-compose up -d
3. **Iniciar a aplicação**
   
