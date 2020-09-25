import { UserSimple } from './user-simple'
import { Question } from './question'
import { Comment } from './comment'

export class Submission {

    constructor(
        public id: number,
        public user: UserSimple,
        public title: string,
        public link: string,
        public question: Question,
        public comments: Array<Comment>,
        public timestamp: number,
        public currentUserApproved: boolean
    ) {}

}