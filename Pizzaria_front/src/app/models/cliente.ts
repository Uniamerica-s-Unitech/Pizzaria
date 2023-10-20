import { Endereco } from "./endereco";

export class Cliente {
    id!:number;
    nome!:string;
    enderecos!: Endereco[];
}
