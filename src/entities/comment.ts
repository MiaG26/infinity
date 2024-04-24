export class Comment {
    public static clone(c: Comment):Comment{
        return new Comment(c.videoId, c.userId, c.text, c.id);
    }

    constructor(
        public videoId: number,
        public userId: number,
        public text: string,
        public id?: number
    ){}

    public toString():string {
        return `id: ${this.id}, videoId: ${this.videoId}, userId: ${this.userId}, text: ${this.text}`;

    }
}