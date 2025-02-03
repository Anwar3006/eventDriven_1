import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import React, { useState } from "react";
import { Toaster } from "../ui/sonner";

const AppProvider = ({ children }: { children: React.ReactNode }) => {
  const [queryClient] = useState(() => new QueryClient());

  return (
    <QueryClientProvider client={queryClient}>
      {/* <NextTopLoader color="#10b981" showSpinner={false} /> */}
      <Toaster />
      {children}
    </QueryClientProvider>
  );
};

export default AppProvider;
