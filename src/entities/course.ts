export class Course {
    public static clone(c: Course):Course{
        return new Course(c.id, c.name, c.description, c.price);
    }

    constructor(
        public id: number,
        public name: string,
        public description: string,
        public price: number

    ){}

    public toString():string {
        return `id: ${this.id}, name: ${this.name}, description: ${this.description}, price: ${this.price}`;

    }
}