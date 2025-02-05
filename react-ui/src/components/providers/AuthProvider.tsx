import { createContext, useEffect, useState } from "react";

export interface User {
  id: string;
  name: string;
  email: string;
}

export interface AuthContextType {
  user: User | null;
  // login: (data: User) => void;
  logout: () => void;
}

export const AuthContext = createContext<AuthContextType | null>({
  user: null,
  // login: () => {},
  logout: () => {},
});

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);

  // const login = (data: User) => {
  //   setUser(data);
  //   localStorage.setItem("user", JSON.stringify(data));
  // };

  const logout = () => {
    setUser(null);
    localStorage.removeItem("user");
  };

  useEffect(() => {
    const storedUser = localStorage.getItem("user");
    if (storedUser) {
      try {
        setUser(JSON.parse(storedUser));
      } catch (error) {
        console.error("Error parsing stored user:", error);
      }
    }
  }, []);

  const contextValue: AuthContextType = {
    user,
    // login,
    logout,
  };

  return (
    <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>
  );
};
