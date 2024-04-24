export class Video {
    public static clone(v: Video):Video{
        return new Video(v.id, v.courseId, v.name, v.description);
    }

    constructor(
        public id: number,
        public courseId: number,
        public name: string,
        public description: string
    ){}

    public toString():string {
        return `id: ${this.id}, courseId: ${this.courseId}, name: ${this.name}, description: ${this.description}`;

    }
}