import { Loader2Icon } from "lucide-react";

const LoadingPage = () => {
  return (
    <div className="w-full h-full flex flex-col justify-center items-center gap-4">
      <Loader2Icon size={40} className="animate-spin stroke-black" />
      <p className="text-2xl">Please wait a moment while we build this page</p>
    </div>
  );
};

export default LoadingPage;
