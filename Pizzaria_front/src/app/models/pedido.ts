import { Cliente } from "./cliente";
import { Produto } from "./produto";

export class Pedido {
    id!: number;
    clienteId!: Cliente;
    produtos!: Produto[];
    solicitacao!: Date;
    finalizacao!: Date;
  }