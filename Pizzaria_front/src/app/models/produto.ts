import { Categoria } from "./categoria";
import { Sabor } from "./sabor";

export class Produto {
    id!: number;
    sabores!: Sabor[];
    categoria!: Categoria;
}
