#language: pt
Funcionalidade: Fluxo completo do pedido

  Cenario: Fluxo completo do pedido
    Quando Cadastrar-se um cliente com o nome "Joaozinho" e o cpf "98765432109"
    E O cliente for cadastrado com sucesso OU o cliente ja existir

    E Cadastrar-se um produto com o nome "MelhorProdutoDeTodos", descricao "AquelaDescricao", preco "123.0", categoria "BEBIDA", imagem "https://via.placeholder.com/150/150", tempoPreparo "10"
    E O produto for cadastrado com sucesso OU o produto ja existir

    E Criar-se um pedido para o cliente com cpf "98765432109"
    E O pedido for criado com sucesso

    E Adicionar-se o produto da categoria "BEBIDA" ao pedido
    E O pedido for atualizado com sucesso

    E Solicitar-se confirmacao para o pedido criado
    E O pedido for confirmado com sucesso

    E O pagamento do pedido for confirmado
    E O status do pedido for atualizado para Aprovado com sucesso

    E Finalizar-se a comanda do pedido
    E A comanda for finalizada com sucesso

    E Informar-se que o pedido esta finalizado
    Entao O status do pedido eh atualizado para "FINALIZADO"

