export type BlogPostResponse = {
  id: string;
  title: string;
  content: string;
  coverImg: string;
  excerpt: string;
  author: BlogPostAuthor;
  status: string;
  subscribers: number[];
  createdAt: string;
};

export type BlogPostAuthor = {
  name: string;
  avatarUrl: string;
};
