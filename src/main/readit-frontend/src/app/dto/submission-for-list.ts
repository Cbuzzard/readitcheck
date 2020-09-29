import { UserSimple } from './user-simple'

export class SubmissionForList {

    constructor (
        public id: number,
        public title: string,
        public link: string,
        public timestamp: number,
        public user: UserSimple,
        public linkPreview: string
    ) {}

}