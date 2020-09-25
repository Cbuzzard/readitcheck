import { UserSimple } from './user-simple'

export class Comment {

    constructor(
        public id: number,
        public content: string,
        public timestamp: number,
        public user: UserSimple
    ) {}

}