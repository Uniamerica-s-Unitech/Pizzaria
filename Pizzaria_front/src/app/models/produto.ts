import { Categoria } from "./categoria";
import { Sabor } from "./sabor";

export class Produto {
    id!: number;
    nome!:string;
    sabores!: Sabor[];
    categoriaId!: Categoria;
}
