# Conta digital

O serviço da **Conta digital** tem basicamente três dados:
- Número da conta
- ID do cliente
- Saldo

E temos algumas operações com ele que são, basicamente, crédito e débito.

Sabendo disso, vamos as tarefas:

1. Criar sistema para creditar em uma conta
2. Criar sistema para debitar em uma conta
 
---

## Creditando um valor em uma conta

### Necessidades

Uma pessoa pode conseguir depositar um valor em sua conta. Por isso, precisamos conseguir que ela credite esse valor em sua conta.
   
### Restrições

- O valor a ser creditado precisa ser positivo

### Resultado Esperado

- Em caso de sucesso:
   - Devolver um status de sucesso (2xx)

- Em caso de falha:
   - Retornar um status de erro (4xx) com as devidas mensagens

### Pontos de atenção
- O que acontece quando duas operações de crédito acontecerem ao mesmo tempo? Como deve ficar o valor final da conta?

---

## Debitando em uma conta

### Necessidades

Sempre que um usuário realiza um pagamento é necessário debitar esse valor de sua conta. 
   
### Restrições

- Só pode ser debitado um valor menor ou igual ao valor do saldo atual da conta.
- O valor a ser debitado precisa ser positivo


### Resultado Esperado

- Em caso de sucesso:
   - Devolver um status de sucesso (2xx)

- Em caso de falha:
   - Retornar um status **422** com a mensagem de erro

### Pontos de atenção
- O que acontece quando duas operações de débito acontecerem ao mesmo tempo?
