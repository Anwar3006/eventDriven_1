import { useState } from "react";
import { motion } from "framer-motion";
import { Zap, PenTool, Database } from "lucide-react";
import { Button } from "@/components/ui/button";
import { useNavigate } from "react-router-dom";

const BlogLandingPage = () => {
  const navigate = useNavigate();
  const [hoveredFeature, setHoveredFeature] = useState(null);

  const pageVariants = {
    initial: { opacity: 0, scale: 0.9 },
    in: { opacity: 1, scale: 1 },
    out: { opacity: 0, scale: 1.1 },
  };

  const pageTransition = {
    type: "tween",
    ease: "anticipate",
    duration: 0.8,
  };

  return (
    <div className="bg-black text-white min-h-screen flex flex-col overflow-hidden relative">
      {/* Abstract Background Layers */}
      <motion.div
        initial={{ opacity: 0.1, rotate: 0 }}
        animate={{
          opacity: [0.1, 0.3, 0.1],
          rotate: [0, 360, 0],
        }}
        transition={{
          duration: 20,
          repeat: Infinity,
          ease: "linear",
        }}
        className="absolute inset-0 bg-gradient-to-br from-purple-900/20 to-blue-900/20 z-0"
      />

      <motion.div
        initial={pageVariants.initial}
        animate={pageVariants.in}
        exit={pageVariants.out}
        transition={pageTransition}
        className="container mx-auto px-6 py-16 relative z-10"
      >
        <div className="text-center">
          <motion.h1
            initial={{ y: -50, opacity: 0 }}
            animate={{ y: 0, opacity: 1 }}
            transition={{ duration: 0.8, type: "spring" }}
            className="text-6xl font-bold mb-6 tracking-tight text-transparent bg-clip-text bg-gradient-to-r from-purple-400 to-pink-600"
          >
            Flux: Your Creative CMS
          </motion.h1>

          <motion.p
            initial={{ opacity: 0, scale: 0.9 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ delay: 0.5, duration: 0.6 }}
            className="text-xl text-gray-300 mb-12 max-w-2xl mx-auto"
          >
            A next-generation content management system that blends artistic
            expression with technological innovation.
          </motion.p>

          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 0.7 }}
            className="flex justify-center space-x-6"
          >
            <Button
              className="bg-gradient-to-r from-purple-600 to-pink-600 px-8 py-6 rounded-full hover:scale-105 transition-transform text-[1rem]"
              onClick={() => navigate("/register")}
            >
              Get Started
            </Button>
            <Button className="border border-white/30 px-8 py-6 rounded-full hover:bg-white/10 transition-colors text-[1rem]">
              Learn More
            </Button>
          </motion.div>
        </div>

        {/* Features Grid */}
        <div className="grid md:grid-cols-3 gap-8 mt-20">
          {[
            {
              icon: <Zap />,
              title: "Lightning Fast",
              description:
                "Rapid content deployment with seamless performance.",
              color: "text-yellow-400",
            },
            {
              icon: <PenTool />,
              title: "Creative Canvas",
              description:
                "Unlimited design possibilities for your digital narrative.",
              color: "text-green-400",
            },
            {
              icon: <Database />,
              title: "Robust Backend",
              description:
                "Secure, scalable infrastructure for your content ecosystem.",
              color: "text-blue-400",
            },
          ].map((feature, index) => (
            <motion.div
              key={feature.title}
              className={`p-6 border border-white/10 rounded-xl backdrop-blur-sm transition-all duration-300 ${
                hoveredFeature === index
                  ? "bg-white/10 scale-105 shadow-2xl"
                  : "bg-white/5"
              }`}
              onMouseEnter={() => setHoveredFeature(null)}
              onMouseLeave={() => setHoveredFeature(null)}
              whileHover={{ scale: 1.05 }}
              initial={{ opacity: 0, y: 50 }}
              animate={{ opacity: 1, y: 0 }}
              transition={{ delay: 1 + index * 0.2 }}
            >
              <div className={`text-4xl mb-4 ${feature.color}`}>
                {feature.icon}
              </div>
              <h3 className="text-xl font-semibold mb-3">{feature.title}</h3>
              <p className="text-gray-400">{feature.description}</p>
            </motion.div>
          ))}
        </div>
      </motion.div>
    </div>
  );
};

export default BlogLandingPage;
